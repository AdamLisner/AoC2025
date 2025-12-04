package day04

import scala.io.Source
import scala.util.Using

enum Item(val symbol: Char) {
  case ROLL extends Item('@')
  case NIL extends Item('.')

  override def toString: String = symbol.toString
}

object Item {
  def fromChar(c: Char): Item = c match {
    case '@' => Item.ROLL
    case '.' => Item.NIL
    case other => throw new IllegalArgumentException(s"Unknown item symbol: '$other'")
  }
}

case class Grid(grid: Vector[Vector[Item]]) {
  def height: Int = grid.length

  def width: Int = if (grid.isEmpty) 0 else grid.head.length

  def getItem(row: Int, col: Int): Option[Item] = {
    if (row < 0 || row >= height || col < 0 || col >= width)
      None
    else
      Some(grid(row)(col))
  }

  def getAdjacentItems(row: Int, col: Int): Vector[Item] = {
    val deltas = Vector(
      (-1, -1), (-1, 0), (-1, 1),
      (0, -1), (0, 1),
      (1, -1), (1, 0), (1, 1)
    )

    deltas.map { case (dr, dc) =>
      getItem(row + dr, col + dc).getOrElse(Item.NIL)
    }
  }

  def removeItems(coords: Seq[(Int, Int)]): Grid = {
    val removals = coords.toSet
    val newGrid = grid.zipWithIndex.map { case (rowVec, r) =>
      rowVec.zipWithIndex.map { case (item, c) =>
        if (removals.contains((r, c))) Item.NIL else item
      }
    }
    Grid(newGrid)
  }
}

class PrintingDepartment {

  def solve(): Int = {
    solve2()
  }

  private def solve1(): Int = {
    val grid = readFile()
    var totalRolls = 0

    for (row <- 0 until grid.height; col <- 0 until grid.width if grid.getItem(row, col).contains(Item.ROLL)) {
      val adjaecentRolls = grid.getAdjacentItems(row, col).filter {
        _ == Item.ROLL
      }
      if (adjaecentRolls.size < 4) {
        totalRolls += 1
      }
    }

    totalRolls
  }

  private def solve2(): Int = {
    var currentGrid = readFile()
    var totalRemoved = 0
    var keepSimulating = true

    while (keepSimulating) {
      val toRemove = for {
        row <- 0 until currentGrid.height
        col <- 0 until currentGrid.width
        if currentGrid.getItem(row, col).contains(Item.ROLL)
        if currentGrid.getAdjacentItems(row, col).count(_ == Item.ROLL) < 4
      } yield (row, col)

      if (toRemove.isEmpty) {
        keepSimulating = false
      } else {
        totalRemoved += toRemove.size
        currentGrid = currentGrid.removeItems(toRemove)
      }
    }

    totalRemoved
  }

  private def readFile(source: String = "src/main/resources/day04.txt"): Grid = {
    Using(Source.fromFile(source)) { src =>
      val lines = src.getLines().toVector
      val grid: Vector[Vector[Item]] = lines.map(_.toVector.map(Item.fromChar))
      Grid(grid)
    }.fold(
      throwable => throw throwable,
      identity
    )
  }
}
