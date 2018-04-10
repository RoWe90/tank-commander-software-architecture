package de.htwg.se.tankcommander.model

class GameState {
  val activePlayer = null;
  var turns = 0

  def increaseTurns(): Unit = {
    turns += 1
  }

  def changeActivePlayer(): Unit = {

  }

  def refreshTUI(): Unit = {

  }

}
