package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.controller.GameStatus
import de.htwg.se.tankcommander.util.Observable

case class GameField() {
  val gridsize_x = 11
  val gridsize_y = 11
  var matchfieldarray = Array.ofDim[Cell](gridsize_x, gridsize_y)
  fillGameFieldWithCells
  fillGameFieldCellsWithObstacles


  def fillGameFieldWithCells(): Unit = {
    var z = 0
    for (_ <- 0 to gridsize_x - 1) {
      for (i <- 0 to gridsize_y - 1) {
        matchfieldarray(i)(z) = new Cell(i, z)
      }
      z += 1
    }
  }


  def fillGameFieldCellsWithObstacles(): Unit = {
    //first row
    val listBush = Array((0, 0), (1, 0), (9, 0), (10, 0),
      //second row
      (0, 1), (1, 1), (9, 1), (10, 1), (0, 9), (1, 9),
      //third row
      (9, 9), (10, 9), (0, 10), (1, 10), (9, 10), (10, 10),
      //rest
      (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6)
    )
    listBush.foreach(j => matchfieldarray(j._1)(j._2).cellobstacle = new Bush)


    val listForest = Array((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7),
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))
    listForest.foreach(j => matchfieldarray(j._1)(j._2).cellobstacle = new Forest)

    val listStone = Array((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))
    listStone.foreach(j => matchfieldarray(j._1)(j._2).cellobstacle = new Rock)

    val listWater = Array((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))
    listWater.foreach(j => matchfieldarray(j._1)(j._2).cellobstacle = new Water)

    val listHill = Array((5, 4), (5, 5), (5, 6))
    listHill.foreach(j => matchfieldarray(j._1)(j._2).cellobstacle = new Hill)
  }


  override def toString: String = {
    var output = new StringBuilder
    for (z <- 0 to gridsize_y - 1) {
      output.append("\n")
      for (i <- 0 to gridsize_x - 1) {
        if (matchfieldarray(i)(z).cellobstacle != null) {
          if (matchfieldarray(i)(z).containsThisTank != null) {
            output.append("T" + "  ")
          } else {
            output.append(matchfieldarray(i)(z).cellobstacle.shortName + "  ")
          }
        } else {
          if (matchfieldarray(i)(z).containsThisTank != null) {
            output.append("T" + "  ")
          } else {
            output.append("o" + "  ")
          }

        }
      }
    }
    output.append(GameStatus.currentHitChance + "\n")
    output.toString()
  }


}
