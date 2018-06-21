package de.htwg.se.tankcommander.controller.controllerComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player

//noinspection ScalaStyle
case class GameStatus() {
  var activePlayer: Option[Player] = Option(GameStatus.activePlayer.get.deepClone())
  var passivePlayer: Option[Player] = Option(GameStatus.passivePlayer.get.deepClone())
  var activeTank: Option[TankModel] = Option(GameStatus.activeTank.get.deepClone())
  var passiveTank: Option[TankModel] = Option(GameStatus.passiveTank.get.deepClone())
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

  def restoreGameStatus(gameStatusBackUp: GameStatus): Unit = {
    this.activePlayer = Option(gameStatusBackUp.activePlayer.get)
    this.passivePlayer = Option(gameStatusBackUp.passivePlayer.get)
    this.activeTank = Option(gameStatusBackUp.activeTank.get)
    this.passiveTank = Option(gameStatusBackUp.passiveTank.get)
    this.movesLeft = gameStatusBackUp.movesLeft
    this.currentPlayerActions = gameStatusBackUp.currentPlayerActions
    this.currentHitChance = gameStatusBackUp.currentHitChance
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

