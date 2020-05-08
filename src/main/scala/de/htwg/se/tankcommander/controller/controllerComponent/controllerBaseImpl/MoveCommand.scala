package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Mover
import de.htwg.se.tankcommander.util.Command

//Success or not yet to be implemented
class MoveCommand(controller: Controller, s: String) extends Command {
  //noinspection ScalaStyle
  var memento: GameFieldInterface = _
  //noinspection ScalaStyle
  var backupGameStatus: GameStatus = _

  override def doStep: Unit = {
    memento = controller.matchfield.deepCopy
    backupGameStatus = controller.createGameStatusBackup
    controller.matchfield = new Mover(controller.matchfield).moveTank(s)
  }

  override def undoStep: Unit = {
    val new_memento = controller.matchfield.deepCopy
    controller.matchfield = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }

  override def redoStep: Unit = {
    val new_memento = controller.matchfield
    controller.matchfield = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2

  }
}

