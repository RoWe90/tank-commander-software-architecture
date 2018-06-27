package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import org.scalatest.{FlatSpec, Matchers}

class GameFieldTest extends FlatSpec with Matchers {
  "A GameField Object" should "have an Array with a size of 11x11" in {
    val gameField = new GameField
    assert(gameField.gridsX === 11 & gameField.gridsY === 11)

  }
  it should "be filled with Cells" in {

    val gameField = new GameField

    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(gameField.marray(x)(y).isInstanceOf[Cell])
      }
    }

    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(gameField.marray(x)(y).isInstanceOf[Cell])
      }
    }
  }
  "Every Cell" should "have been filled with an Obstacle-Option" in {
    val gameField = new GameField

    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        assert(gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Bush]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Hill]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Rock]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Forest]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Water]])
      }
    }
  }
  "A GameField Object" should "print out the Gamefield with toString" in {
    val gameField = new GameField
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        gameField.marray(x)(y).cobstacle = Option(new Bush)
      }
    }
    val tank1 = new TankModel
    val tank2 = new TankModel
    gameField.marray(0)(0).containsThisTank = Option(tank1)
    gameField.marray(10)(10) = new Cell(10, 10)
    gameField.marray(10)(10).containsThisTank = Option(tank2)
    gameField.marray(9)(10) = new Cell(9, 10)
    assert(gameField.toString === "\n" +
      "T  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  o  T  HS: 100\n"
    )

  }

}
