package de.htwg.se.tankcommander.model

case class Player(namePar: String) {
  val name = namePar;
  override def toString: String = name

}

