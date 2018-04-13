package de.htwg.se.tankcommander.model.matchfield

import de.htwg.se.tankcommander.model.playerdata.TankModel

class Field {
  private var a = Array.ofDim[Cell](10, 10)
  fillField()

  def fillField(): Unit = {
    var z = 0
    for (_ <- 0 to 10) {
      for (i <- 0 until a.length) {
        a(i)(z) = new Cell(i, z)
      }
      z += 1
    }
  }

  def setPositionOfModel(xtank: Integer, ytank: Integer, tank1: TankModel): Unit = {
    a(tank1.position.x)(tank1.position.y).containsThisTank = null
    tank1.position(null)
    tank1.position(a(xtank)(ytank))
    a(xtank)(ytank).containsThisTank = tank1
  }

  def createMap(): Unit = {

  }
}
