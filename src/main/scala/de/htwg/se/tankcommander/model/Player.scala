package de.htwg.se.tankcommander.model

case class Player(namePar: String) {
  val name = namePar;
  val currentPlayerTank: TankModel = null;
  override def toString: String = name

}

