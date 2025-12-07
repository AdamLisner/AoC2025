package day07

import scala.collection.mutable
import scala.io.Source
import scala.util.Using

class Laboratories {

  def solve() = {
    solve2()
  }

  private def solve1() : Long = {
    val lines = readInput()

    val startIndex = lines.head.indexOf('S')

    var visited = Set.empty[(Int, Int)]

    def go(x: Int, y: Int): Long = {
      if (y >= lines.length || x >= lines(y).length || x < 0) {
        0
      } else if (lines(y)(x) == '^') {
        if (visited.contains((x, y))) {
          0
        } else {
          visited += ((x, y))
          go(x - 1, y + 1) + go(x + 1, y + 1) + 1
        }
      } else {
        go(x, y + 1)
      }
    }

    go(startIndex, 0)
  }

  private def solve2(): Long = {
    val lines = readInput()

    if (lines.isEmpty) return 0L

    val startX = lines.head.indexOf('S')

    val memo = mutable.Map[(Int, Int), Long]()

    def go(x: Int, y: Int): Long = {
      if (y >= lines.length || y < 0) return 1L
      if (x < 0 || x >= lines(y).length) return 1L
      if (memo.contains((x, y))) {
        return memo((x, y))
      }

      val result = if (lines(y)(x) == '^') {
        go(x - 1, y + 1) + go(x + 1, y + 1)
      } else {
        go(x, y + 1)
      }

      memo((x, y)) = result
      result
    }

    go(startX, 0)
  }

  private def readInput(source: String = "src/main/resources/day07.txt"): Array[String] = {
    Using(Source.fromFile(source)) { src =>
      src.getLines().toArray
    }.getOrElse(Array.empty)
  }

}
