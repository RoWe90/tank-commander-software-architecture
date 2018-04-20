package de.htwg.se.tankcommander.model.matchfield

import de.htwg.se.tankcommander.model.playerdata.TankModel

class Field {
  val gridsize_x = 10
  val gridsize_y = 10
  private val matchfieldarray = Array.ofDim[Cell](gridsize_x, gridsize_y)
  fillField()

  def fillField(): Unit = {
    var z = 0
    for (_ <- 0 to 10) {
      for (i <- 0 until matchfieldarray.length) {
        matchfieldarray(i)(z) = new Cell(i, z)
      }
      z += 1
    }
  }

  def setPositionTank(xtank: Integer, ytank: Integer, tank1: TankModel): Unit = {

    if (tank1.position.x != null && tank1.position.y != null) {
      matchfieldarray(tank1.position.x)(tank1.position.y).containsThisTank = null
      tank1.position(null)
      tank1.position(matchfieldarray(xtank)(ytank))
      matchfieldarray(xtank)(ytank).containsThisTank = tank1
    }
  }

  def createMap(): Unit = {

  }
}
