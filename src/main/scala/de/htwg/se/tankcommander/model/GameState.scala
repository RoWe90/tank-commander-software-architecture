package de.htwg.se.tankcommander.model

class GameState(spielfeldpar: GameField) {
  var activePlayer: Player = null;
  var currentPlayerActions = 2;
  var gameStarted = false;
  var spielfeld: GameField = spielfeld
  var players = Array(null, null)
  var turns = 0
  GameField.apply()
  def increaseTurns(): Unit = {
    turns += 1
  }

  def setUpGame(): Unit = {
    print("Welcome to Tank-Commander")
    print("Player 1 please choose your Name")
    val player1 = Player(scala.io.StdIn.readLine())
    print("Player 2 please choose your Name")
    val player2 = Player(scala.io.StdIn.readLine())
    print("Player 1 please name your Tank")
    var tank1 = new TankModel(scala.io.StdIn.readLine())
    print("Player 1 please name your Tank")
    val tank2 = new TankModel(scala.io.StdIn.readLine())

    spielfeld.setPositionTank((3, 3) tank1)
    spielfeld.setPositionTank((6, 6) tank2)
    players
    .0 = player1
    players
    .1 = player2
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
