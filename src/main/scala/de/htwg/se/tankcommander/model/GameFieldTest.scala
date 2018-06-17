package de.htwg.se.tankcommander.model

import org.scalatest.{FlatSpec, Matchers, PrivateMethodTester}

class GameFieldTest extends FlatSpec with Matchers with PrivateMethodTester {
  //@TODO: How to test private Methods?!
  "A GameField Object" should "have an Array with a size of 11x11" in {
    val gameField = GameField
    val gameFieldTestTest = PrivateMethod[GameField]('fillGameFieldWithCells)

    for (y <- 0 until 11) {
      for (x <- 0 until 11) {
        //      assert((x,y) === invokePrivate gameFieldTestTest())
      }
    }

  }

}
