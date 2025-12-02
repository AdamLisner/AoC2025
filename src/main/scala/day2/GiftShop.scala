package day2

import scala.io.Source
import scala.util.Using

class GiftShop {

  def solve(resource: String = "src/main/resources/day02.txt"): Long = {
    val input = readPairsFromResource(resource)
    input
  }

  private def readPairsFromResource(resourceName: String): Long = {
    Using(Source.fromFile(resourceName)) { src =>
      src.getLines()
        .flatMap(_.split(","))
        .map(_.trim)
        .filter(_.nonEmpty)
        .map { s =>
          val parts = s.split("-", 2)
          (parts(0).toLong, parts(1).toLong)
        }
        .map { case (start, end) =>
          Iterator.range(start, end + 1).map(isSticked).sum
        }
        .sum
    }.fold(
      throwable => throw throwable,
      identity
    )
  }

  private def isSticked(a: Long): Long = {
    val str = a.toString
    val len = str.length

    if (len == 1) return 0L

    val half = len / 2

    val hasPattern = (1 to half).exists { patternLen =>
      len % patternLen == 0 && {
        val numReps = len / patternLen
        (patternLen until len).forall(pos => str(pos % patternLen) == str(pos))
      }
    }

    if (hasPattern) a else 0L
  }
}