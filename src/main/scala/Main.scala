import day03.Lobby
import day04.PrintingDepartment
import day05.Cafeteria

object Main extends App {
  val solver = new Cafeteria()
  val password = solver.solve()
  println(s"The password is: $password")
}

