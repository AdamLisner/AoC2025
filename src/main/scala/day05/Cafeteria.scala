package day05

import scala.annotation.tailrec
import scala.io.Source
import scala.util.Using

case class Range(start: Long, end: Long)

class Cafeteria(var ranges: List[Range] = List.empty) {

  def solve(): (Long, Long) = {
    val part1 = solve1()
    val part2 = freshIngredientsCount()
    (part1,part2)
  }

  private def solve1(resource: String = "src/main/resources/day05.txt"): Long = {
    Using(Source.fromFile(resource)) { src =>
      val lines = src.getLines().map(_.trim).toList

      val (rangeLines, idLines) = lines.span(_.nonEmpty)

      this.ranges = rangeLines.map(parseRange)

      idLines
        .filter(_.nonEmpty)
        .map(_.toLong)
        .count(id => isFresh(id, ranges))
        .toLong

    }.fold(
      throwable => throw throwable,
      identity
    )
  }

  private def freshIngredientsCount(resource: String = "src/main/resources/day05.txt"): Long = {
    Using(Source.fromFile(resource)) { src =>
      val ranges = src.getLines()
        .map(_.trim)
        .takeWhile(_.nonEmpty)
        .map(parseRange)
        .toList

      val sortedRanges = ranges.sortBy(_._1)

      if (sortedRanges.isEmpty) 0L
      else {
        mergeAndCount(sortedRanges.tail, sortedRanges.head._1, sortedRanges.head._2, 0L)
      }

    }.fold(
      throwable => throw throwable,
      identity
    )
  }

  @tailrec
  private def mergeAndCount(remaining: List[Range], currentStart: Long, currentEnd: Long, acc: Long): Long = {
    remaining match {
      case Nil =>
        acc + (currentEnd - currentStart + 1)
      case Range(nextStart, nextEnd) :: tail =>
        if (nextStart > currentEnd) {
          val currentLength = currentEnd - currentStart + 1
          mergeAndCount(tail, nextStart, nextEnd, acc + currentLength)
        } else {
          val newEnd = math.max(currentEnd, nextEnd)
          mergeAndCount(tail, currentStart, newEnd, acc)
        }
    }
  }

  private def parseRange(line: String): Range = {
    val parts = line.split('-')
    Range(parts(0).toLong, parts(1).toLong)
  }

  /**
   * Check whether `id` falls into any of the ranges using binary search over a Vector
   * of non-overlapping ranges. This requires the ranges to be sorted by start.
   */
  private def isFresh(id: Long, ranges: List[Range]): Boolean = {
    ranges.exists { case Range(start, end) =>
      id >= start && id <= end
    }
  }
}
