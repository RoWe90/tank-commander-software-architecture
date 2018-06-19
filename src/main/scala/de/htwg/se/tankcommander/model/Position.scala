package de.htwg.se.tankcommander.model

//i only exist for type-safety reasons

//noinspection ScalaStyle
case class Position(x: Integer, y: Integer) {

  def DeepClone(): Position = {
  val posClone = new Position(this.x,this.y)
    posClone
  }
}
