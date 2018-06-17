package de.htwg.se.tankcommander.model

class Cell(pos: Position) {
  val x: Int = pos.x
  val y: Int = pos.y
  var cobstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None
}