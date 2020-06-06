package tankcommander.controllerComponent

import tankcommander.gameState.GameStatus

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  def setUpGame(): Unit

  def fillGameFieldWithTanks(pos: (Int, Int), pos2: (Int, Int)): Unit

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

  def gamefieldToHtml(): String

}
