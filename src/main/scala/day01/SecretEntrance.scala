package day01

import scala.io.Source

class SecretEntrance(val startPos: Int = 50, val ticks: Int = 100) {

  def solve(resourceName: String = "src/main/resources/day01.txt"): Long = {
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