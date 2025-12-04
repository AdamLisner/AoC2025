import day03.Lobby
import day04.PrintingDepartment

object Main extends App {
  val solver = new PrintingDepartment()
  val password = solver.solve()
  println(s"The password is: $password")
}

