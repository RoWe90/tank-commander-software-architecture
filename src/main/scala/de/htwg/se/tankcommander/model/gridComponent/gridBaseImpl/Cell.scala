package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import scala.xml._

case class Cell(pos: (Int, Int), cobstacle: Option[Obstacle] = None, containsThisTank: Option[TankModel] = None) {
  val x: Int = pos._1
  val y: Int = pos._2

  def deepClone(): Cell = {
    val cellClone = Cell((this.x, this.y), this.cobstacle.get.deepClone(), Option(this.containsThisTank.get.copy()))
    cellClone
  }
}