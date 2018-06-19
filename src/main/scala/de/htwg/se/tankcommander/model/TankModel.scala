package de.htwg.se.tankcommander.model

//noinspection ScalaStyle
class TankModel() {
  val tankBaseDamage: Int = 10
  val accuracy: Int = 100
  var hp: Int = 100
  var posC: Option[Cell] = None
  var facing:String = "up"

  override def clone(): TankModel = {
    val tankClone = new TankModel
    tankClone.hp = this.hp
    tankClone.posC = this.posC.clone()
    tankClone.facing = this.facing
    tankClone
  }
}

