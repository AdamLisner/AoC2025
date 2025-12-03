package day03

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using

class Lobby {

  def solve(resource: String = "src/main/resources/day03.txt"): Long = {
    Using(Source.fromFile(resource)) { src =>
      src.getLines()
        .map(_.trim)
        .filter(_.nonEmpty)
        .map(line => processLine(line))
        .sum
    }.fold(
      throwable => throw throwable,
      identity
    )
  }

  def processLine(line: String): Long = {

    var lineLen = line.length
    val arr = line.toCharArray.map {
      case n if n.isDigit => n.asDigit
      case _ => 0
    }

    @tailrec
    def go(from: Int, n : Int, array: Array[Int], acc: Long): Long = {
      if (n == 0) return acc
      val len = array.length - n + 1
      val (maxIndex, maxValue) = findMaxIndexAndValue(array.slice(0, len))
      go(maxIndex + 1, n - 1, array.slice(maxIndex + 1, array.length), 10 * acc + maxValue)
    }

    go(from = 0, n = 12, array = arr, acc = 0L)
  }

  private def findMaxIndexAndValue(arr: Array[Int]): (Int, Int) = {
    var maxIndex = -1
    var maxValue = -1

    for (i <- arr.indices) {
      if (arr(i) > maxValue) {
        maxValue = arr(i)
        maxIndex = i
      }
    }

    (maxIndex, maxValue)
  }


}
