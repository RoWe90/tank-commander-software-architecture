package de.htwg.se.tankcommander.model

//noinspection ScalaStyle
class Cell(pos: Position) {
  val x: Int = pos.x
  val y: Int = pos.y
  var cobstacle: Option[Obstacle] = None
  var containsThisTank: Option[TankModel] = None


  override def clone(): Cell = {
    var cellClone = new Cell(new Position(this.x,this.y))
    cellClone.cobstacle = this.cobstacle.clone()
    cellClone.containsThisTank = this.containsThisTank.clone()
    cellClone
  }
}