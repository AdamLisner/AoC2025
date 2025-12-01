import day01.SecretEntrance

@main def run(): Unit = {
  val solver = new SecretEntrance()
  val password = solver.solve()
  println(s"The password is: $password")
}

