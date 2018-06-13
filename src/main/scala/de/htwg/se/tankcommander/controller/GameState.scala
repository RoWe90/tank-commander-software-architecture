package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{Player, TankModel}

//noinspection ScalaStyle
object GameStatus extends Enumeration {


  type GameStatus = Value
  val PLAYER1, PLAYER2 = Value

  val map = Map[GameStatus, String](
    PLAYER1 -> "",
    PLAYER2 -> "Game successfully solved")
  var passiveTank: TankModel = null
  var activePlayer: Player = null
  var activeTank: TankModel = null
  var movesLeft = true
  var gameStarted = false;
  var currentPlayerActions = 2
  var canHit = false
  if (currentPlayerActions == 0) movesLeft = false else movesLeft = true
  var currentHitRate = 0
  var players: Array[Player] = new Array[Player](2)
  if (currentHitRate == 0) canHit = false else canHit = true

  def message(gameStatus: GameStatus) = {
    map(gameStatus)
  }

  def xyz(bool: Boolean, integer: Int): Unit = {
    if (bool) {
      currentHitRate = integer
    } else {
      currentHitRate = 0
    }

  }

  def changeActivePlayer(): Unit = {
    if (activePlayer == players(0)) {
      activePlayer = players(1)
      currentPlayerActions = 2
      println("Change Players")
    } else {
      activePlayer = players(0)
      currentPlayerActions = 2
      println("Change Players")
    }
  }
}
