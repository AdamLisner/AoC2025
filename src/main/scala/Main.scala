import day03.Lobby
import day04.PrintingDepartment
import day05.Cafeteria
import day06.TrashCompactor
import day07.Laboratories
import day08.Playground

object Main extends App {
  val solver = new Playground()
  val password = solver.solve()
  println(s"The password is: $password")
}

