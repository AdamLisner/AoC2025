package day03

import org.scalatest.funsuite.AnyFunSuite

class LobbyTest extends AnyFunSuite {

  test("testProcessLine") {
    // arrange
    val lobby = new Lobby()
    val line = "818181911112111"

    // act
    val result = lobby.processLine(line)

    // assert
    assert(result == 888911112111L)
  }

}
