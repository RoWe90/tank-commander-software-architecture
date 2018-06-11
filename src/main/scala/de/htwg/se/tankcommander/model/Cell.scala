package de.htwg.se.tankcommander.model

class Cell(x1: Integer, y1: Integer) {
  val x = x1;
  val y = y1;
  var cellterrain: Obstacle = null
  var cellPowerUP: PowerUP = null;
  var cellItem: Item = null;
  var containsThisTank: TankModel = null

}
