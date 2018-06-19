package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{GameField, Player, TankModel}
import de.htwg.se.tankcommander.util.{Observable, UndoManager}

//noinspection ScalaStyle
class Controller(var matchfield: GameField) extends Observable {
  private val undoManager = new UndoManager

  /*
   * Methods to get the Game running or end it
   */
  def setUpGame(): Unit = {
    matchfield = new GameField
    print("Welcome to Tank-Commander\n" + "Player 1 please choose your Name" + "\n")
    val player1 = Player(scala.io.StdIn.readLine())
    print("Player 2 please choose your Name" + "\n")
    val player2 = Player(scala.io.StdIn.readLine())
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    fillGameFieldWithTank((0, 0), tank1, (10, 5), tank2)
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    notifyObservers()
  }

  def fillGameFieldWithTank(pos: (Int, Int), tank: TankModel, pos2: (Int, Int), tank2: TankModel): Unit = {
    tank.posC = pos
    tank2.posC = pos2
    matchfield.marray(pos._1)(pos._2).containsThisTank = Option(tank)
    matchfield.marray(pos2._1)(pos2._2).containsThisTank = Option(tank)
  }

  /*
  * Methods to do stuff
  */

  def endTurnChangeActivePlayer(): Unit = {
    print("Runde beendet Spielerwechsel")
    GameStatus.changeActivePlayer()
    //lineOfSightContainsTank()
    notifyObservers()
  }

  /*
   * Methods to shoot stuff
   */

  def createGameStatusBackup: GameStatus = {
    val backup = new GameStatus
    backup
  }
  def checkIfPlayerHasMovesLeft(): Boolean = {
    if (GameStatus.movesLeft) {
      true
    } else {
      print("no turns left")
      false
    }
  }
  def matchfieldToString: String = matchfield.toString

  /*
 * Undo manager
 */

  def move(s: String): Unit = {
    undoManager.doStep(new MoveCommand(this, s))
    notifyObservers()
  }

  def shootC(): Unit = {
    undoManager.doStep(new ShootCommand(this))
    notifyObservers()
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers()
  }

}


