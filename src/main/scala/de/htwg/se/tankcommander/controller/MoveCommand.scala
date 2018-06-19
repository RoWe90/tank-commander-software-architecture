package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.util.Command
import de.htwg.se.tankcommander.model.{GameField, Mover}

//Success or not yet to be implemented
class MoveCommand(controller: Controller, s: String) extends Command {
  var memento: GameField = new GameField
  var backupGameStatus: GameStatus = new GameStatus

  override def doStep: Unit = {
    memento = controller.matchfield.backupGameField
    backupGameStatus = controller.createGameStatusBackup
    controller.matchfield = new Mover(controller.matchfield).moveTank(s)
  }

  override def undoStep: Unit = {
    val new_memento = controller.matchfield
    controller.matchfield = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(new_memento2)
    backupGameStatus = controller.createGameStatusBackup

  }

  override def redoStep: Unit = {
    val new_memento = controller.matchfield
    controller.matchfield = memento
    memento = new_memento

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(new_memento2)
    backupGameStatus = controller.createGameStatusBackup

  }
}

