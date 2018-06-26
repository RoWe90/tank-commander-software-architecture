package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject}
import de.htwg.se.tankcommander.TankCommanderModule
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.controller.controllerComponent.{ControllerInterface, GameStatus}
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{GameField, TankModel}
import de.htwg.se.tankcommander.model.playerComponent.Player
import de.htwg.se.tankcommander.util.{Observable, UndoManager}
import net.codingwell.scalaguice.InjectorExtensions._

import scala.swing.Publisher

class Controller @Inject()(var matchfield: GameFieldInterface) extends Observable with Publisher with ControllerInterface {
  private val undoManager = new UndoManager
  val injector = Guice.createInjector(new TankCommanderModule)
  val fileIO = injector.instance[FileIOInterface]


  override def setUpGame(): Unit = {
    matchfield = new GameField
    print("Welcome to Tank-Commander\n" + "Player 1 please choose your Name" + "\n")
    val player1 = Player(Console.readLine)
    print("Player 2 please choose your Name" + "\n")
    val player2 = Player(Console.readLine)
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    fillGameFieldWithTank((0, 5), tank1, (10, 5), tank2)
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    notifyObservers()
  }

  override def fillGameFieldWithTank(pos: (Int, Int), tank: TankModel, pos2: (Int, Int), tank2: TankModel): Unit = {
    tank.posC = pos
    tank2.posC = pos2
    matchfield.marray(pos._1)(pos._2).containsThisTank = Option(tank)
    matchfield.marray(pos2._1)(pos2._2).containsThisTank = Option(tank)
  }

  override def endTurnChangeActivePlayer(): Unit = {
    print("Runde beendet Spielerwechsel")
    GameStatus.changeActivePlayer()
    //lineOfSightContainsTank()
    notifyObservers()
  }

  override def createGameStatusBackup: GameStatus = {
    val backup = new GameStatus
    backup
  }

  override def checkIfPlayerHasMovesLeft(): Boolean = {
    if (GameStatus.movesLeft) {
      true
    } else {
      print("no turns left")
      false
    }
  }

  override def matchfieldToString: String = matchfield.toString

  /*
 * Undo manager
 */

  override def move(s: String): Unit = {
    undoManager.doStep(new MoveCommand(this, s))
    notifyObservers()
  }

  override def shoot(): Unit = {
    undoManager.doStep(new ShootCommand(this))
    notifyObservers()
  }

  override def undo(): Unit = {
    undoManager.undoStep
    notifyObservers()
  }

  override def redo(): Unit = {
    undoManager.redoStep
    notifyObservers()
  }

  override def save(): Unit = {
    fileIO.save()
  }

  override def load(): Unit = {
    matchfield = new GameField;
    fileIO.load(this)
    notifyObservers()
  }

}


