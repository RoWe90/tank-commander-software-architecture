package de.htwg.se.tankcommander.model

class Cell(x1: Integer, y1: Integer) {
  val x = x1;
  val y = y1;
  var cellterrain: Obstacle = null
  var cellPowerUP: PowerUP = null;
  var cellUpgrade: Upgrade = null;
  var cellItem: Item = null;
  var containsThisTank: TankModel = null

  def setCellContent(x: PowerUP): Unit = {
    cellPowerUP = x;
  }

  def setCellContent(x: Upgrade): Unit = {
    cellUpgrade = x;
  }

  def setCellContent(x: Item): Unit = {
    cellItem = x;
  }

  def setCellContent(x: Obstacle): Unit = {
    cellterrain = x;
  }

  def removeCellContentTerrain(): Unit = {
    cellterrain = null;
  }

  def removeCellContentPUI(): Unit = {
    cellItem = cellUpgrade = cellPowerUP = null;
  }

  def removeTank(x: TankModel): Unit = {
    containsThisTank = null;
  }

}
