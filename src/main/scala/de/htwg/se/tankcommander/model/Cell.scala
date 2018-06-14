package de.htwg.se.tankcommander.model

class Cell(x1: Integer, y1: Integer) {
  val x:Int = x1
  val y:Int = y1
  var cobstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None
}

