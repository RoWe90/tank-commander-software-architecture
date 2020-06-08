package tankcommander.controllerComponent.fileIoComponent.fileIoJsonImpl

import play.api.libs.json._
import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.controllerComponent.fileIoComponent.FileIOInterface
import tankcommander.gameState.GameStatus
import tankcommander.model.girdComponent.gridBaseImpl.Cell
import tankcommander.util.AttributeHandler

import scala.io.Source

class FileIO extends FileIOInterface {

  val attributeHandler = AttributeHandler()

  override def save(): Unit = {
    import java.io._
    val file = new File("src/main/ressources/savegame.json")
    file.createNewFile()
    val pw = new PrintWriter(file)
    pw.write(Json.prettyPrint(GameStateToJson))
    pw.close()
  }

  def GameStateToJson: JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "aPlayer" -> JsString(GameStatus.activePlayer.get),
        "pPlayer" -> JsString(GameStatus.passivePlayer.get),
        "movesCount" -> JsNumber(GameStatus.currentPlayerActions),
        "hitchance" -> JsNumber(GameStatus.currentHitChance),
        "posATankX" -> JsNumber(attributeHandler.getPosC(GameStatus.activeTank.get)._1),
        "posATankY" -> JsNumber(attributeHandler.getPosC(GameStatus.activeTank.get)._2),
        "posPTankX" -> JsNumber(attributeHandler.getPosC(GameStatus.passiveTank.get)._1),
        "posPTankY" -> JsNumber(attributeHandler.getPosC(GameStatus.passiveTank.get)._2),
        "aTankHP" -> JsNumber(attributeHandler.getAttribute(GameStatus.activeTank.get, "hp").toInt),
        "pTankHP" -> JsNumber(attributeHandler.getAttribute(GameStatus.passiveTank.get, "hp").toInt),
        "aTankFacing" -> JsString(attributeHandler.getAttribute(GameStatus.activeTank.get, "facing")),
        "pTankFacing" -> JsString(attributeHandler.getAttribute(GameStatus.passiveTank.get, "facing")),
        "currentHS" -> JsNumber(GameStatus.currentHitChance),
        "movesLeft" -> JsBoolean(GameStatus.movesLeft)
      )
    )
  }

  override def load(controller: Controller): Unit = {
    val source: String = Source.fromFile("src/main/ressources/savegame.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    GameStatus.activePlayer = Some((json \ "game" \ "aPlayer").get.toString())
    GameStatus.passivePlayer = Some((json \ "game" \ "pPlayer").get.toString())
    GameStatus.currentPlayerActions = (json \ "game" \ "movesCount").get.toString().toInt
    GameStatus.currentHitChance = (json \ "game" \ "hitchance").get.toString().toInt

    attributeHandler.setAttribute(1, "hp", (json \ "game" \ "aTankHP").get.toString())
    attributeHandler.setAttribute(1, "hp", (json \ "game" \ "posATankX").get.toString() + "_" + (json \ "game" \ "posATankY").get.toString())
    attributeHandler.setAttribute(1, "hp", (json \ "game" \ "aTankFacing").get.toString())

    attributeHandler.setAttribute(2, "hp", (json \ "game" \ "pTankHP").get.toString())
    attributeHandler.setAttribute(2, "hp", (json \ "game" \ "posPTankX").get.toString() + "_" + (json \ "game" \ "posPTankY").get.toString())
    attributeHandler.setAttribute(2, "hp", (json \ "game" \ "pTankFacing").get.toString())

    GameStatus.activeTank = Some(1)
    GameStatus.passiveTank = Some(2)
    GameStatus.currentHitChance = (json \ "game" \ "currentHS").get.toString().toInt
    GameStatus.movesLeft = (json \ "game" \ "movesLeft").get.toString().toBoolean

    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      attributeHandler.getPosC(GameStatus.activeTank.get)._1, controller.matchfield.mvector(attributeHandler.getPosC(GameStatus.activeTank.get)._1).updated(
        attributeHandler.getPosC(GameStatus.activeTank.get)._2, Cell(attributeHandler.getPosC(GameStatus.activeTank.get),
          controller.matchfield.mvector(attributeHandler.getPosC(GameStatus.activeTank.get)._1)(attributeHandler.getPosC(GameStatus.activeTank.get)._1).cobstacle, Some(GameStatus.activeTank.get)))))

    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      attributeHandler.getPosC(GameStatus.passiveTank.get)._1, controller.matchfield.mvector(attributeHandler.getPosC(GameStatus.passiveTank.get)._1).updated(
        attributeHandler.getPosC(GameStatus.passiveTank.get)._2, Cell(attributeHandler.getPosC(GameStatus.passiveTank.get),
          controller.matchfield.mvector(attributeHandler.getPosC(GameStatus.passiveTank.get)._1)(attributeHandler.getPosC(GameStatus.passiveTank.get)._1).cobstacle, Some(GameStatus.passiveTank.get)))))
    //    controller.matchfield.mvector(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
    //      .containsThisTank = Option(tank1)
    //    controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
    //      .containsThisTank = Option(tank2)
  }
}
