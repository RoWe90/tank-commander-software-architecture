package de.htwg.se.tankcommander.model.terrain_obstacles

class Rock extends Obstacle {
  override val name: String = "Stein"
  override val desc: String = ""
  override val passable: Boolean = false
}
