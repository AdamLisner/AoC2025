package day06

import scala.collection.mutable.ListBuffer
import scala.io.Source
import scala.util.Using

sealed trait Operation {
  def apply(a: Long, b: Long): Long
}

case object Add extends Operation {
  override def apply(a: Long, b: Long): Long = a + b
}

case object Multiply extends Operation {
  override def apply(a: Long, b: Long): Long = a * b
}

object Operation {
  def fromString(op: String): Operation = op match {
    case "+" => Add
    case "*" => Multiply
    case _ => throw new IllegalArgumentException(s"Unknown operation: $op")
  }
}

class TrashCompactor {

  def solve(): Long = {
    solve2()
  }

  def solve1(): Long = {
    val (operations, numbers) = readInput()

    val results =
      numbers.transpose.zip(operations).map({ case (nums, op) =>
        nums.map(_.toLong).reduce(op.apply)
      }).sum

    results
  }

  def solve2(): Long = {
    val lines = Using(Source.fromFile("src/main/resources/day06.txt"))(_.getLines().toList).getOrElse(List.empty)
    if (lines.isEmpty) return 0L

    val maxLen = lines.map(_.length).max
    val grid = lines.map(_.padTo(maxLen, ' '))
    val height = grid.length

    val isSeparator = (0 until maxLen).map { x =>
      (0 until height).forall(y => grid(y)(x) == ' ')
    }

    val blocks = ListBuffer[List[Long]]()
    val currentBlock = ListBuffer[Long]()

    for (x <- 0 until maxLen) {
      if (isSeparator(x)) {
        if (currentBlock.nonEmpty) {
          blocks += currentBlock.toList
          currentBlock.clear()
        }
      } else {
        currentBlock += x
      }
    }

    if (currentBlock.nonEmpty) {
      blocks += currentBlock.toList
    }

    blocks.map { cols =>
      val opChar = cols.map(x => grid.last(x.toInt)).find(_ != ' ').getOrElse('+')
      val operation = Operation.fromString(opChar.toString)
      
      val numbers = cols.reverse.flatMap { x =>
        val numString = (0 until height - 1).map(y => grid(y)(x.toInt)).filterNot(_ == ' ').mkString
        if (numString.nonEmpty) Some(numString.toLong) else None
      }
      
      numbers.reduce(operation.apply)
      
    }.sum

  }

  private def readInput(source: String = "src/main/resources/day06.txt"): (List[Operation], List[List[String]]) = {
    Using(Source.fromFile(source)) { src =>
      val input = src.getLines().map(_.split("\\s+").filterNot(_.isBlank).toList).toList
      val operations = input.last.map(Operation.fromString)
      val numbers = input.dropRight(1)
      (operations, numbers)
    }.fold(
      throwable => throw throwable,
      (operations, numbers) => (operations, numbers)
    )
  }
}
