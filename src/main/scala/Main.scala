import day01.SecretEntrance

@main def run(): Unit = {
  val solver = new SecretEntrance()
  val password = solver.solvePart1()
  println(s"The password is: $password")
  val password2 = solver.solvePart2()
  println(s"The password for part 2 is: $password2")
}

