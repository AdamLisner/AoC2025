import day2.GiftShop

object Main extends App {
  val solver = new GiftShop()
  val password = solver.solve()
  println(s"The password is: $password")
}

