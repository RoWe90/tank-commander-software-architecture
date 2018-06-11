package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.model.Cell
import de.htwg.se.tankcommander.model._


class GameFieldCreator {
  var spielfeld: GameField = new GameField


  def fillField(): Unit = {
    def fillField(): Unit = {
      var z = 0
      for (_ <- 0 to 10) {
        for (i <- 0 until spielfeld.matchfieldarray.length) {
          spielfeld.matchfieldarray(i)(z) = new Cell(i, z)
        }
        z += 1
      }
    }
  }

  def createMap(): Unit = {
    //first row
    val listBush = Array((0, 0), (1, 0), (9, 0), (10, 0), (0, 1),
      //second row
      (1, 1), (9, 1), (10, 1), (0, 9), (1, 9),
      //third row
      (9, 9), (10, 9), (0, 10), (1, 10), (9, 10), (10, 10),
      //rest
      (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6)
    )
    listBush.foreach(j => spielfeld.matchfieldarray(j._1)(j._2).cellterrain = new Bush)


    val listForest = Array((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7),
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))
    listForest.foreach(j => spielfeld.matchfieldarray(j._1)(j._2).cellterrain = new Forest)

    val listStone = Array((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))
    listStone.foreach(j => spielfeld.matchfieldarray(j._1)(j._2).cellterrain = new Rock)

    val listWater = Array((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))
    listWater.foreach(j => spielfeld.matchfieldarray(j._1)(j._2).cellterrain = new Water)

    val listHill = Array((5, 4), (5, 5), (5, 6))
    listHill.foreach(j => spielfeld.matchfieldarray(j._1)(j._2).cellterrain = new Hill)


  }

  def setPositionTank(xtank: Integer, ytank: Integer, tank1: TankModel, spielfeld: GameField): Unit = {

    if (tank1.position.x != null && tank1.position.y != null) {
      spielfeld.matchfieldarray(tank1.position.x)(tank1.position.y).containsThisTank = null
      tank1.position(null)
      tank1.position(spielfeld.matchfieldarray(xtank)(ytank))
      spielfeld.matchfieldarray(xtank)(ytank).containsThisTank = tank1
    }
  }

}
