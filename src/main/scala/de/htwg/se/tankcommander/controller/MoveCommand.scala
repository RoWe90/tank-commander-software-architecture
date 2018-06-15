package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.util.Command
import de.htwg.se.tankcommander.model.{GameField}

class MoveCommand(controller: Controller) extends Command {
  var backupMatchfield: GameField = controller.matchfield

  override def doStep: Unit = {
  }

  override def undoStep: Unit = {
    val new_memento = controller.matchfield
    controller.matchfield = backupMatchfield
    backupMatchfield = new_memento
  }

  override def redoStep: Unit = {
    val new_memento = controller.matchfield
    controller.matchfield = backupMatchfield
    backupMatchfield = new_memento
  }
}

