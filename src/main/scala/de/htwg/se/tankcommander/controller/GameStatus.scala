package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{Player, TankModel}

//noinspection ScalaStyle
class GameStatusBackUp {
  var activePlayer: Option[Player] = GameStatus.activePlayer
  var passivePlayer: Option[Player] = GameStatus.passivePlayer
  var activeTank: Option[TankModel] = GameStatus.activeTank
  var passiveTank: Option[TankModel] = GameStatus.passiveTank
  var movesLeft: Boolean = GameStatus.movesLeft
  var currentPlayerActions: Int = GameStatus.currentPlayerActions
  var currentHitChance: Int = GameStatus.currentHitChance
}

object GameStatus {
  var activePlayer: Option[Player] = None
  var passivePlayer: Option[Player] = None
  var activeTank: Option[TankModel] = None
  var passiveTank: Option[TankModel] = None
  var movesLeft: Boolean = true
  var currentPlayerActions: Int = 2
  var currentHitChance: Int = 0

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

  def restoreGameStatus(gameStatusBackUp: GameStatusBackUp): Unit = {
    activePlayer = gameStatusBackUp.activePlayer
    passivePlayer = gameStatusBackUp.passivePlayer
    activeTank = gameStatusBackUp.activeTank
    passiveTank = gameStatusBackUp.passiveTank
    movesLeft = gameStatusBackUp.movesLeft
    currentPlayerActions = gameStatusBackUp.currentPlayerActions
    currentHitChance = gameStatusBackUp.currentHitChance
  }

  def increaseTurns(): Unit = {
    GameStatus.currentPlayerActions -= 1
    if (GameStatus.currentPlayerActions == 0) {
      GameStatus.movesLeft = false
    }
  }

  def endGame(): Unit = {
    print(GameStatus.activePlayer.get + " Won\n")
  }

}

