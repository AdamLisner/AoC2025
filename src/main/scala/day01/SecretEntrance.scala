package day01

import scala.io.Source
import scala.util.Using

class SecretEntrance(val startPos: Int = 50, val ticks: Int = 100) {

  def solvePart1(resourceName: String = "src/main/resources/day01.txt"): Long = {
    Using(Source.fromFile(resourceName)) { source =>
      val (_, zeroCount) = source.getLines()
        .filter(_.nonEmpty)
        .foldLeft((startPos, 0)) { case ((pos, count), line) =>
          val cleanLine = line.trim
          val dirChar = cleanLine.head
          val dist = cleanLine.tail.trim.toInt

          val nextPos =
            if (dirChar == 'L') Math.floorMod(pos - dist, ticks)
            else Math.floorMod(pos + dist, ticks)

          (nextPos, if (nextPos == 0) count + 1 else count)
        }
      zeroCount.toLong
    }.fold(
      throwable => throw throwable,
      zeroCount => zeroCount
    )
  }

  def solvePart2(resourceName: String = "src/main/resources/day01.txt"): Long = {
    val source = Source.fromFile(resourceName)
    try {
      var pos = startPos
      var totalZeros = 0L

      val lines = source.getLines().filter(line => line.nonEmpty && (line.head == 'L' || line.head == 'R'))

      for (line <- lines) {
        val cleanLine = line.trim
        val dir = cleanLine.head
        val dist = cleanLine.tail.toInt

        val stepsToZero = if (dir == 'R') {
          if (pos == 0) 100 else 100 - pos
        } else {
          if (pos == 0) 100 else pos
        }

        if (dist >= stepsToZero) {
          totalZeros += 1 + (dist - stepsToZero) / ticks
        }

        if (dir == 'L') {
          pos = Math.floorMod(pos - dist, ticks)
        } else {
          pos = Math.floorMod(pos + dist, ticks)
        }
      }
      totalZeros
    } finally {
      source.close()
    }
  }
}
