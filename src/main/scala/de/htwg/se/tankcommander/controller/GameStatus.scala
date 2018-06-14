package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{Player, TankModel}

//noinspection ScalaStyle
object GameStatus {
  var activePlayer: Option[Player] = None
  var passivePlayer: Option[Player] = None
  var activeTank: Option[TankModel] = None
  var passiveTank: Option[TankModel] = None
  var movesLeft = true
  var currentPlayerActions = 2

  var currentHitChance = 0


  def setCurrentHitrate(bool: Boolean, integer: Int): Unit = {
    if (bool) {
      currentHitChance = integer
    } else {
      currentHitChance = 0
    }
  }

  def changeActivePlayer(): Unit = {
    val temp = activePlayer
    val temp2 = activeTank
    activePlayer = passivePlayer
    passivePlayer = temp
    activeTank = passiveTank
    passiveTank = temp2
    currentPlayerActions = 2
    movesLeft = true
  }
}
