package de.htwg.se.tankcommander.controller.controllerComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel

import scala.swing.Publisher

trait ControllerInterface extends Publisher{
  def setUpGame(): Unit

  def fillGameFieldWithTank(pos: (Int, Int), tank: TankModel, pos2: (Int, Int), tank2: TankModel): Unit

  def endTurnChangeActivePlayer(): Unit

  def createGameStatusBackup: GameStatus

  def checkIfPlayerHasMovesLeft(): Boolean

  def matchfieldToString: String

  def move(s: String): Unit

  def shoot(): Unit

  def undo(): Unit

  def redo(): Unit

  def save(): Unit

  def load(): Unit
}
