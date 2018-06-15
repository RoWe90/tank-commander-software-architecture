package de.htwg.se.tankcommander.model

class TankModel() {
  val tankBaseDamage = 10
  val accuracy = 100
  var healthpoints = 100
  var posC: Option[Cell] = None
  var facing = "up"
}

