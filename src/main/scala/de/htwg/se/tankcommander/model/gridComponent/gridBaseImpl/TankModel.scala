package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import scala.xml.Elem

class TankModel() {
  val tankBaseDamage: Int = 10
  val accuracy: Int = 100
  var hp: Int = 100
  var posC: (Int, Int) = (0, 0)
  var facing: String = "up"

  def deepClone(): TankModel = {
    val tankClone = new TankModel
    tankClone.hp = this.hp
    tankClone.posC = this.posC
    tankClone.facing = this.facing
    tankClone
  }
}

