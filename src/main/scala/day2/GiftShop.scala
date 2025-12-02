package day2
import scala.io.Source
import scala.util.Using

class GiftShop {

  def solve(resource: String = "src/main/resources/day02.txt"): Long = {
    val input = readPairsFromResource(resource)
    input.foldLeft(0L)((acc, pair) => {
      val (start, end) = pair

      val sumOfInvalidIdsInRange = (start to end).foldLeft(0L)((subAcc, num) => {
        if (isSticked(num)) subAcc + num else subAcc
      })

      acc + sumOfInvalidIdsInRange
    })
  }

  private def readPairsFromResource(resourceName: String): List[(Long, Long)] = {
    Using(Source.fromFile(resourceName)) { src =>

      src.getLines()
        .flatMap(_.split(","))
        .map(_.trim)
        .filter(_.nonEmpty)
        .map { s =>
          val parts = s.split("-", 2)
          (parts(0).toLong, parts(1).toLong)
        }
        .toList
    } .fold(
      throwable => throw throwable,
      list => list
    )
  }

  private def isSticked(a: Long): Boolean = {
    val str = a.toString
    val len = str.length

    val half = len / 2

    (1 to half).exists { i =>
      len % i == 0 && {
        val pattern = str.substring(0, i)
        val repeated = pattern * (len / i)
        repeated == str
      }
    }
  }
}