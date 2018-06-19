package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.obsolete.Position

class Cell(pos: (Int,Int)) {
  val x: Int = pos._1
  val y: Int = pos._2
  var cobstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None

  def deepClone(): Cell = {
    val cellClone = new Cell(this.x, this.y)
    if (this.cobstacle.isDefined) {
      cellClone.cobstacle = Option(this.cobstacle.get.deepClone())
    }
    if (this.containsThisTank.isDefined) {
      cellClone.containsThisTank = Option(this.containsThisTank.get.deepClone())
    }
    cellClone
  }
}