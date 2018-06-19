package de.htwg.se.tankcommander.model

class TankModel() {
  val tankBaseDamage: Int = 10
  val accuracy: Int = 100
  var hp: Int = 100
  var posC: Option[Cell] = None
  var facing: String = "up"

  def deepClone(): TankModel = {
    val tankClone = new TankModel
    tankClone.hp = this.hp
    if (this.posC.isDefined) {
      tankClone.posC = Option(this.posC.get.deepClone())
    }
    tankClone.facing = this.facing
    tankClone
  }
}

