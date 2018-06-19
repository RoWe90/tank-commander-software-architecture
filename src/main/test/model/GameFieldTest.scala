package model

import de.htwg.se.tankcommander.model._
import org.scalatest.{FlatSpec, Matchers}

class GameFieldTest extends FlatSpec with Matchers {
  //@TODO: How to test private Methods?!
  "A GameField Object" should "have an Array with a size of 11x11" in {
    val gameField = new GameField
    assert(gameField.gridsX === 11 & gameField.gridsy === 11)


  }
  it should "fill a GameField with Cells" in {

    val gameField = new GameField


    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsy) {
        assert(gameField.marray(x)(y).isInstanceOf[Cell])
      }
    }

    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsy) {
        assert(gameField.marray(x)(y).isInstanceOf[Cell])
      }
    }
  }
  "Every Cell" should "have been filled with an Obstacle-Option" in {
    val gameField = new GameField

    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsy) {
        assert(gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Bush]] === true | gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Hill]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Rock]] === true | gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Forest]] === true |
          gameField.marray(x)(y).cobstacle.isInstanceOf[Option[Water]])
      }
    }
  }

}
