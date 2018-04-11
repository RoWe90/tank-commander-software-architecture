package de.htwg.se.tankcommander.model

case class Player(nameI: String) {
  val name = nameI;
  val currentPlayerTank: TankModel = null;
  var activePlayer: Boolean = false

  override def toString: String = name

}

