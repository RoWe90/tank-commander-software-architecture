package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{GameField, Player, TankModel}
import de.htwg.se.tankcommander.util.Command

class ShootCommand(controller: Controller) extends Command {
  val gamestatusatthattime = GameStatus

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
