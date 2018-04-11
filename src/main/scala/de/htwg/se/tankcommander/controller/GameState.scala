package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.Player

class GameState(initPlayer1: Player, initPlayer2: Player) {
  var activePlayer = initPlayer1;

  var players = Array(initPlayer1, initPlayer2)
  var turns = 0

  def increaseTurns(): Unit = {
    turns += 1
  }

  def changeActivePlayer(activePlayerX: Player, players: Array[Player]): Unit = {
    if (players(0).activePlayer) {
      players(0).activePlayer = false
      players(1).activePlayer = true
      activePlayer = players(1)
    } else {
      players(1).activePlayer = false
      players(0).activePlayer = true
      activePlayer = players(0)
    }
  }

  def refreshTUI(): Unit = {

  }

}
