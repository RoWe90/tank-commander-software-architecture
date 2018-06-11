package de.htwg.se.tankcommander.model

//Items are temporary usable buffs stored in the players inventory.
trait Item {
  val itemName: String
  val description: String
  val duration: Integer

  def activate(item: Item): Integer = {
    duration
  }

  //Remove Buff from player, return true when successful
  def deactivate(item: Item): Boolean = {
    false
  }

  override def toString: String = {
    itemName

  }
}

//+1 this turn, -1 next turn
class Kaffee extends Item {
  val itemName: String = "Kaffee"
  val description: String = "+1 Turns now, -1 Turns next"
  val duration: Integer = 2

}

//small health up
class Reparaturkit extends Item {
  val itemName: String = "Reparaturkit"
  val description: String = "Small HP up"
  val duration: Integer = 0
}

//acc up
class Zielwasser extends Item {

  val itemName: String = "Zielwasser"
  val description: String = "Increases Accuracy temporarily"
  val duration: Integer = 2
}




