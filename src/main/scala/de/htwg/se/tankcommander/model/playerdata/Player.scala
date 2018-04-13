package de.htwg.se.tankcommander.model.playerdata

case class Player(namePar: String) {
  val name = namePar;
  val currentPlayerTank: TankModel = null;
  var activePlayer: Boolean = false
  var playerInventory: Inventory = new Inventory


  override def toString: String = name

}

