package controller.baseImpl

import controller.PlayerControllerInterface
import model.FileIOInterface
import playerComponent.Player

import scala.util.{Failure, Success}

case class PlayerController(var player: Player, fileIo: FileIOInterface) extends PlayerControllerInterface {

  override def initPlayer(name: String): Unit = {
    player.initPlayer(name)
  }

  override def save(): Unit = {
    fileIo.save(player, "PlayerModule")
  }

  override def load(): Unit = {
    player = fileIo.load(player, "PlayerModule")match {
      case Failure(_) => return
      case Success(playerInfo) => playerInfo
    }
  }

  override def playerName: String = player.toString

}

case class InitPlayerContainer(name: String)

object InitPlayerContainer {
  import play.api.libs.json._
  implicit val containerWrites: OWrites[InitPlayerContainer] = Json.writes[InitPlayerContainer]
  implicit val containerReads: Reads[InitPlayerContainer] = Json.reads[InitPlayerContainer]
}
