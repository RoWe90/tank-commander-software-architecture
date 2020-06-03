package tankcommander.controllerComponent.controllerBaseImpl

import gridComponent.gameState.GameStatus
import gridComponent.gridBaseImpl.Shooter
import tankcommander.util.Command

class ShootCommand(controller: Controller) extends Command {
  val shooter = new Shooter
  //noinspection ScalaStyle
  var backupGameStatus: GameStatus = _

  override def doStep(): Unit = {
    backupGameStatus = controller.createGameStatusBackup
    shooter.shoot()
  }

  override def undoStep(): Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }

  override def redoStep(): Unit = {

    val new_memento2 = controller.createGameStatusBackup
    GameStatus.restoreGameStatus(backupGameStatus)
    backupGameStatus = new_memento2
  }
}

