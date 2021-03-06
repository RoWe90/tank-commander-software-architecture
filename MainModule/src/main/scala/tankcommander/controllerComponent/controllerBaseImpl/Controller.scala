package tankcommander.controllerComponent.controllerBaseImpl

import com.google.inject.{Guice, Inject, Injector}
import net.codingwell.scalaguice.InjectorExtensions._
import tankcommander.TankCommanderModule
import tankcommander.controllerComponent.ControllerInterface
import tankcommander.controllerComponent.fileIoComponent.FileIOInterface
import tankcommander.gameState.GameStatus
import tankcommander.model.GameFieldFactory
import tankcommander.model.girdComponent.GameFieldInterface
import tankcommander.model.girdComponent.gridBaseImpl.Cell
import tankcommander.util.{AttributeHandler, Observable, UndoManager}

import scala.swing.Publisher

class Controller @Inject()() extends Observable with Publisher with ControllerInterface {

  val attributeHandler = new AttributeHandler

  var matchfield: GameFieldInterface = GameFieldFactory.apply("Map 1")
  var mapChosen: String = ""
  private val undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new  TankCommanderModule)
  val fileIO: FileIOInterface = injector.instance[FileIOInterface]

  def this(matchfield2: GameFieldInterface) {
    this()
    matchfield = matchfield2
  }

  override def setUpGame(): Unit = {
    print("Welcome to Tank-Commander\n" + "Player 1 please choose your Name" + "\n")
    attributeHandler.setPlayerHttp(scala.io.StdIn.readLine(), 1)
    print("Player 2 please choose your Name" + "\n")
    attributeHandler.setPlayerHttp(scala.io.StdIn.readLine(), 2)
    print("Choose your Map: Map 1 or Map 2" + "\n")
    mapChosen = scala.io.StdIn.readLine()
    matchfield = GameFieldFactory.apply(mapChosen)
    fillGameFieldWithTanks((0, 5), (10, 5))
    GameStatus.activePlayer = Some(attributeHandler.getPlayerHttp(1))
    GameStatus.passivePlayer = Some(attributeHandler.getPlayerHttp(2))
    notifyObservers()
  }

  def setUpGame(name1: String, name2: String, map: String): Unit = {
    attributeHandler.setPlayerHttp(name1, 1)
    attributeHandler.setPlayerHttp(name2, 2)
    mapChosen = map
    matchfield = GameFieldFactory.apply(mapChosen)
    fillGameFieldWithTanks((0, 5), (10, 5))
    GameStatus.activePlayer = Some(attributeHandler.getPlayerHttp(1))
    GameStatus.passivePlayer = Some(attributeHandler.getPlayerHttp(2))
    notifyObservers()
  }



  override def fillGameFieldWithTanks(pos: (Int, Int), pos2: (Int, Int)): Unit = {
    attributeHandler.setAttribute(1, "init", pos._1 + "_" + pos._2)
    GameStatus.activeTank = Some(1)
    attributeHandler.setAttribute(2, "init", pos2._1 + "_" + pos2._2)
    GameStatus.passiveTank = Some(2)
    matchfield = matchfield.update(matchfield.mvector.updated(
      pos._1, matchfield.mvector(pos._1).updated(
        pos._2, Cell(pos, matchfield.mvector(pos._1)(pos._2).cobstacle, Some(1)))))

    matchfield = matchfield.update(matchfield.mvector.updated(
      pos2._1, matchfield.mvector(pos2._1).updated(
        pos2._2, Cell(pos2, matchfield.mvector(pos2._1)(pos2._2).cobstacle, Some(2)))))

  }

  override def endTurnChangeActivePlayer(): Unit = {
    print("Runde beendet Spielerwechsel")
    GameStatus.changeActivePlayer()
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

  override def matchfieldToString: String = matchfield.displayField()

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
    undoManager.undoStep()
    notifyObservers()
  }

  override def redo(): Unit = {
    undoManager.redoStep()
    notifyObservers()
  }

  override def save(): Unit = {
    //fileIO.save()

    attributeHandler.save()
  }

  override def load(): Unit = {
    matchfield = GameFieldFactory.apply(mapChosen)
    //fileIO.load(this)

    attributeHandler.load()
    GameStatus.updatePlayers()

    val tank1posC = attributeHandler.getPosC(1)
    val tank2posC = attributeHandler.getPosC(2)

    matchfield = matchfield.update(matchfield.mvector.updated(
      tank1posC._1, matchfield.mvector(tank1posC._1).updated(
        tank1posC._2, Cell(tank1posC, matchfield.mvector(tank1posC._1)(tank1posC._2).cobstacle, Some(1)))))

    matchfield = matchfield.update(matchfield.mvector.updated(
      tank2posC._1, matchfield.mvector(tank2posC._1).updated(
        tank2posC._2, Cell(tank2posC, matchfield.mvector(tank2posC._1)(tank2posC._2).cobstacle, Some(2)))))

    notifyObservers()
  }

  override def gamefieldToHtml(): String = {
    matchfield.toHtml
  }

}


