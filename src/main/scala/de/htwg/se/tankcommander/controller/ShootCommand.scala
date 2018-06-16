package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.util.Command
import de.htwg.se.tankcommander.model.{GameField, Shooter}

class ShootCommand(controller: Controller) extends Command {
  var backupGameStatus: GameStatusBackUp = controller.createGameStatusBackup

  override def doStep: Unit = {

    backupGameStatus = controller.createGameStatusBackup
    var shooter = new Shooter
    shooter.shoot()
  }

  override def undoStep: Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(new_memento2)
    backupGameStatus = controller.createGameStatusBackup
  }

  override def redoStep: Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(new_memento2)
    backupGameStatus = controller.createGameStatusBackup
  }
}

