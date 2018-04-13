package de.htwg.se.tankcommander.model.terrain

class Water extends Obstacle {
  override val name: String = "Wasser"
  override val desc: String = ""
  override val passable: Boolean = false
}
