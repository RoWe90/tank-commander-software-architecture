package de.htwg.se.tankcommander.model


import de.htwg.se.tankcommander.model.Obstacles.Obstacle
import de.htwg.se.tankcommander.model.PowerUPsSub.PowerUPs

class Cell(x1: Integer, y1: Integer) {
  val x = x1;
  val y = y1;
  val terrain: Obstacle = null
  var cellContainsPowerUP: PowerUPs = null;
  var containsThisTank: TankModel = null

}
