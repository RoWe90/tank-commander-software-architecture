package de.htwg.se.tankcommander.model.playerComponent

case class Player(name: String) {
  override def toString: String = name
  def deepClone(): Player ={
    Player(this.name)
  }
}

