package de.htwg.se.tankcommander.model.terrain

trait Obstacle {
  val name: String
  val desc: String
  val passable: Boolean

}
