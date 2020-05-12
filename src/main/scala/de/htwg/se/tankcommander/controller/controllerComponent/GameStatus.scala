package de.htwg.se.tankcommander.controller.controllerComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player

//noinspection ScalaStyle
case class GameStatus() {
  var activePlayer: Option[Player] = GameStatus.activePlayer match {
    case Some(i) => Some(i.copy()) //eventuell deepclone
    case None => None
  }
  var passivePlayer: Option[Player] = GameStatus.passivePlayer match {
    case Some(i) => Some(i.copy()) //eventuell deepclone
    case None => None
  }
  var activeTank: Option[TankModel] = GameStatus.activeTank match {
    case Some(i) => Some(i.copy())
    case None => None
  }
  var passiveTank: Option[TankModel] = GameStatus.passiveTank match {
    case Some(i) => Some(i.copy())
    case None => None
  }
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
    this.activePlayer = gameStatusBackUp.activePlayer match {
      case Some(i) => Some(i.copy())
      case None => None
    }
    this.passivePlayer = gameStatusBackUp.passivePlayer match {
      case Some(i) => Some(i.copy())
      case None => None
    }
    this.activeTank = gameStatusBackUp.activeTank match {
      case Some(i) => Some(i.copy())
      case None => None
    }
    this.passiveTank = gameStatusBackUp.passiveTank match {
      case Some(i) => Some(i.copy())
      case None => None
    }
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
    GameStatus.activePlayer match {
      case Some(i) => print(i + " Won\n")
      case None => print("Error couldn't get Winner")
    }
  }
  def resetGameStatus() : Unit = {
    GameStatus.activePlayer = None
    GameStatus.passivePlayer = None
    GameStatus.activeTank = None
    GameStatus.passiveTank = None
    GameStatus.movesLeft = true
    GameStatus.currentPlayerActions = 2
    GameStatus.currentHitChance = 0
  }
}

