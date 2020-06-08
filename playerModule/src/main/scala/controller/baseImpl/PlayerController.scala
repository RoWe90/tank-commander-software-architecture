package controller.baseImpl

import controller.PlayerControllerInterface
import model.FileIOInterface
import playerComponent.Player

import scala.util.{Failure, Success}

case class PlayerController(var player1: Player, var player2: Player) extends PlayerControllerInterface {

  override def initPlayer(name: String, whichPlayer: Int): Unit = {
    whichPlayer match {
      case 1 => player1 = new Player(name)
      case 2 => player2 = new Player(name)
      case _ =>
    }
  }

  /*
  override def save(): Unit = {
    fileIo.save(player, "PlayerModule")
  }

  override def load(): Unit = {
    player = fileIo.load(player, "PlayerModule")match {
      case Failure(_) => return
      case Success(playerInfo) => playerInfo
    }
  }
*/
  override def playerNameList: List[String] = List(player1.toString, player2.toString)

}
