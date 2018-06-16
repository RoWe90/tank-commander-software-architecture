package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{Player, TankModel}

class GameStatusBackUp {
  var activePlayer: Option[Player] = GameStatus.activePlayer
  var passivePlayer: Option[Player] = GameStatus.passivePlayer
  var activeTank: Option[TankModel] = GameStatus.activeTank
  var passiveTank: Option[TankModel] = GameStatus.passiveTank
  var movesLeft = GameStatus.movesLeft
  var currentPlayerActions = GameStatus.currentPlayerActions
  var currentHitChance = GameStatus.currentHitChance
}
