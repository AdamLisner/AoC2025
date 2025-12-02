import day01.SecretEntrance
import day2.GiftShop

@main def run(): Unit = {
  val solver = new GiftShop()
  val password = solver.solve()
  println(s"The password is: $password")

}

