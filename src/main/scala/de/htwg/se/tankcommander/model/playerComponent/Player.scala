package de.htwg.se.tankcommander.model.playerComponent

import scala.xml.Elem

case class Player(name: String) {
  override def toString: String = name

  def deepClone(): Player = {
    Player(this.name)
  }
}

