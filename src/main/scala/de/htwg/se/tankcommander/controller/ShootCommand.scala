package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.util.Command
import de.htwg.se.tankcommander.model.GameField

class ShootCommand(controller: Controller) extends Command {
  var memento: GameField = controller.matchfield
  var backupGameStatus: GameStatusBackUp = controller.createGameStatusBackup

  override def doStep: Unit = {
    memento = controller.matchfield
    backupGameStatus = controller.createGameStatusBackup

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

