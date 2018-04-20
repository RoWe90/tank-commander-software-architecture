package de.htwg.se.tankcommander.model.gamestate

import de.htwg.se.tankcommander.model.playerdata.Player

class GameState(initPlayer1: Player, initPlayer2: Player) {
  var activePlayer = initPlayer1;
  var currentPlayerActions = 2;

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
      currentPlayerActions = 2
    } else {
      players(1).activePlayer = false
      players(0).activePlayer = true
      activePlayer = players(0)
      currentPlayerActions = 2
    }
  }

  def refreshTUI(): Unit = {

  }

}
