package de.htwg.se.tankcommander.model

//Items are temporary usable buffs stored in the players inventory. Power falls in between Upgrades<Items<PowerUps
trait Item {
   val itemName : String
   val description : String
  //time for the duration of the item, as soon as it's activated
  val duration : Integer

//activate Item, apply buffs, return duration
  def activate(item: Item):Integer ={
    duration
  }
  //Remove Buff from player, return true when successful
  def deactivate(item: Item):Boolean={
    false
  }
  //+1 this turn, -1 next turn
  class Kaffee
  //small health up
  class Reparaturkit
  //acc up
  class Zielwasser
}
