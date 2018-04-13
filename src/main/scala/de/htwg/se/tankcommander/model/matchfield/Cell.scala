package de.htwg.se.tankcommander.model.matchfield

import de.htwg.se.tankcommander.model.items.Item
import de.htwg.se.tankcommander.model.playerdata.TankModel
import de.htwg.se.tankcommander.model.terrain_obstacles.Obstacle

class Cell(x1: Integer, y1: Integer) {
  val x = x1;
  val y = y1;
  val terrain: Obstacle = null
  var cellContainsPowerUP: Item = null;
  var containsThisTank: TankModel = null

}
