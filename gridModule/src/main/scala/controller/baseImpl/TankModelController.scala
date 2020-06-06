package controller.baseImpl

import controller.TankModelControllerInterface
import model.FileIOInterface
import tankModelComponent.TankModel

import scala.util.{Failure, Success}

case class TankModelController(var tankModel: TankModel, fileIo: FileIOInterface) extends TankModelControllerInterface {

  override def initPlayer(name: String): Unit = {
    tankModel.initPlayer(name)
  }

  override def save(): Unit = {
    fileIo.save(tankModel, "PlayerModule")
  }

  override def load(): Unit = {
    tankModel = fileIo.load(tankModel, "PlayerModule") match {
      case Failure(_) => return
      case Success(playerInfo) => playerInfo
    }
  }

  override def playerName: String = tankModel.toString

}

case class InitPlayerContainer(name: String)

object InitPlayerContainer {

  import play.api.libs.json._

  implicit val containerWrites: OWrites[InitPlayerContainer] = Json.writes[InitPlayerContainer]
  implicit val containerReads: Reads[InitPlayerContainer] = Json.reads[InitPlayerContainer]
}
