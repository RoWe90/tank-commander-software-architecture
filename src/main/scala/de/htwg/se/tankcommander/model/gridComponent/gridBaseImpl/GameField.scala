package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface

class GameField() extends GameFieldInterface {
  override val gridsX = 11
  override val gridsY = 11
  override var marray: Array[Array[Cell]] = Array.ofDim[Cell](gridsX, gridsY)
  fillGameFieldWithCells()
  fillGameFieldCellsWithObstacles()

  override def fillGameFieldWithCells(): Unit = {
    for (x <- 0 until gridsX) {
      for (y <- 0 until gridsY) {
        marray(x)(y) = new Cell(x, y)
      }
    }
  }

  override def fillGameFieldCellsWithObstacles(): Unit = {
    //first row
    val listBush = Array((0, 0), (1, 0), (9, 0), (10, 0),
      //second row
      (0, 1), (1, 1), (9, 1), (10, 1), (0, 9), (1, 9),
      //third row
      (9, 9), (10, 9), (0, 10), (1, 10), (9, 10), (10, 10),
      //rest
      (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6)
    )
    listBush.foreach(j => marray(j._1)(j._2).cobstacle = Option(new Bush))
    val listForest = Array((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7),
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))
    listForest.foreach(j => marray(j._1)(j._2).cobstacle = Option(new Forest))
    val listStone = Array((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))
    listStone.foreach(j => marray(j._1)(j._2).cobstacle = Option(new Rock))
    val listWater = Array((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))
    listWater.foreach(j => marray(j._1)(j._2).cobstacle = Option(new Water))
    val listHill = Array((5, 4), (5, 5), (5, 6))
    listHill.foreach(j => marray(j._1)(j._2).cobstacle = Option(new Hill))
  }

  override def toString: String = {
    var output = new StringBuilder
    for (z <- 0 until gridsY) {
      output.append("\n")
      for (i <- 0 until gridsX) {
        if (marray(i)(z).cobstacle.isDefined) {
          if (marray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append(marray(i)(z).cobstacle.get.shortName + "  ")
          }
        } else {
          if (marray(i)(z).containsThisTank.isDefined) {
            output.append("T" + "  ")
          } else {
            output.append("o" + "  ")
          }
        }
      }
    }
    output.append("HS: " + GameStatus.currentHitChance + "\n")
    output.toString()
  }

  override def deepCopy: GameField = {
    val gameFieldClone: GameField = new GameField
    for (x <- 0 until gridsX) {
      for (y <- 0 until gridsY) {
        gameFieldClone.marray(x)(y) = this.marray(x)(y).deepClone()
      }
    }
    gameFieldClone
  }
}
