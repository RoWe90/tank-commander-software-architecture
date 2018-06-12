package de.htwg.se.tankcommander.model


//TODO Define a class containing all needed values and methods for a tank-object
class TankModel(name: String) {
  val tankName = name
  val tankBaseDamage = 10
  val accuracy = 100
  var healthpoints = 100
  var position: Cell = null
  var facing = ""

  def getPositionAsIntY(): Int = {
    if (position != null) {
      this.position.y
    }
    else {
      1000
    }
  }

  def getPositionAsIntX(): Int = {
    if (position != null) {
      this.position.x
    }
    else {
      1000
    }
  }

}

