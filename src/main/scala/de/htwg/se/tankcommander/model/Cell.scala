package de.htwg.se.tankcommander.model

class Cell(pos: Position) {
  val x: Int = pos.x
  val y: Int = pos.y
  var cobstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None

  def deepClone(): Cell = {
    val cellClone = new Cell(Position(this.x, this.y))
    if (this.cobstacle.isDefined) {
      cellClone.cobstacle = Option(this.cobstacle.get.deepClone())
    }
    if (cellClone.containsThisTank.isDefined) {
      cellClone.containsThisTank = Option(this.containsThisTank.get.deepClone())
    }
    cellClone
  }
}