import day03.Lobby

object Main extends App {
  val solver = new Lobby()
  val password = solver.solve()
  println(s"The password is: $password")
}

