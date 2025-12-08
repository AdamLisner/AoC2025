package day08

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.util.Using

case class Point(x: Int, y: Int, z: Int)

case class Edge(p1: Int, p2: Int, distSq: Long)


class Playground {

  def solve(): Long = {
    solve2()
  }

  private def solve1(): Long = {
    val points = readInput()
    val n = points.size

    val edges = ArrayBuffer[Edge]()
    for (i <- 0 until n) {
      for (j <- i + 1 until n) {
        val p1 = points(i)
        val p2 = points(j)
        val dx = p1.x.toLong - p2.x.toLong
        val dy = p1.y.toLong - p2.y.toLong
        val dz = p1.z.toLong - p2.z.toLong
        val distSq = dx * dx + dy * dy + dz * dz
        edges += Edge(i, j, distSq)
      }
    }

    val sortedEdges = edges.sortBy(_.distSq)

    val unionFind = new UnionFind(n)

    for (edge <- sortedEdges.take(1000)) {
      unionFind.union(edge.p1, edge.p2)
    }

    val finalCircuitSizes = (0 until n)
      .filter(i => unionFind.find(i) == i)
      .map(i => unionFind.rank(i).toLong)
      .sorted(Ordering[Long].reverse)

    finalCircuitSizes.take(3).product
  }

  private def solve2(): Long = {
    val points = readInput()
    val n = points.size

    val edges = ArrayBuffer[Edge]()
    for (i <- 0 until n) {
      for (j <- i + 1 until n) {
        val p1 = points(i)
        val p2 = points(j)
        val dx = p1.x.toLong - p2.x.toLong
        val dy = p1.y.toLong - p2.y.toLong
        val dz = p1.z.toLong - p2.z.toLong
        val distSq = dx * dx + dy * dy + dz * dz
        edges += Edge(i, j, distSq)
      }
    }

    val sortedEdges = edges.sortBy(_.distSq)

    val unionFind = new UnionFind(n)

    var connectionsMade = 0
    var result = -1L
    for (edge <- sortedEdges if connectionsMade < n - 1) yield {
      if (unionFind.union(edge.p1, edge.p2)) {
        connectionsMade += 1

        if (connectionsMade == n - 1) {
          val point1 = points(edge.p1)
          val point2 = points(edge.p2)
          result = point1.x.toLong * point2.x.toLong
        }
      }
    }

    result
  }

  def readInput(source: String = "src/main/resources/day08.txt") : List[Point] = {
    Using(Source.fromFile(source)) { src =>
      src.getLines().map { line =>
        val parts = line.trim.split(",").map(_.toInt)
        Point(parts(0), parts(1), parts(2))
      }.toList
    }.fold(
      throwable => throw throwable,
      identity
    )
  }
}

class UnionFind(size: Int) {
  val parent: Array[Int] = (0 until size).toArray
  val rank: Array[Int] = Array.fill(size)(1)

  def find(i: Int): Int = {
    if (parent(i) != i) {
      parent(i) = find(parent(i))
    }
    parent(i)
  }

  def union(i: Int, j: Int): Boolean = {
    val rootI = find(i)
    val rootJ = find(j)
    if (rootI != rootJ) {
      if (rank(rootI) < rank(rootJ)) {
        parent(rootI) = rootJ
      } else if (rank(rootI) > rank(rootJ)) {
        parent(rootJ) = rootI
      } else {
        parent(rootJ) = rootI
        rank(rootI) += 1
      }
      true
    } else {
      false
    }
  }
}