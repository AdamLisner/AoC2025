import day03.Lobby
import day04.PrintingDepartment
import day05.Cafeteria
import day06.TrashCompactor

object Main extends App {
  val solver = new TrashCompactor()
  val password = solver.solve()
  println(s"The password is: $password")
}

