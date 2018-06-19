package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Shooter
import de.htwg.se.tankcommander.util.Command

class ShootCommand(controller: Controller) extends Command {
  val shooter = new Shooter
  //noinspection ScalaStyle
  var backupGameStatus: GameStatus = null

  override def doStep: Unit = {
    backupGameStatus = controller.createGameStatusBackup
    shooter.shoot()
  }

  override def undoStep: Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }

  override def redoStep: Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }
}

