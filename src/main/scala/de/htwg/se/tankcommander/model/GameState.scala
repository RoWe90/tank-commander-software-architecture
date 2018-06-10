package de.htwg.se.tankcommander.model

class GameState() {
  var activePlayer: Player = null;
  var currentPlayerActions = 2;
  var gameStarted = false;

  var players = Array(null, null)
  var turns = 0

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
    val tank1 = new TankModel(scala.io.StdIn.readLine())
    print("Player 1 please name your Tank")
    val tank2 = new TankModel(scala.io.StdIn.readLine())
    val map: GameField = new GameField
    map.setPositionTank(3, 3, tank1)
    map.setPositionTank(8, 8, tank2)
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
