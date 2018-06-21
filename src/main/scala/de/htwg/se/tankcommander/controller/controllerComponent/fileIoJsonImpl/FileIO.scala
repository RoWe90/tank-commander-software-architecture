package de.htwg.se.tankcommander.controller.controllerComponent.fileIoJsonImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import play.api.libs.json._
import scala.io.Source

class FileIO(controller: Controller) {
  def save: Unit = {
    import java.io._
    val pw = new PrintWriter(new File("savegame.json"))
    pw.write(Json.prettyPrint(GameStateToJson))
    pw.close
  }

  def GameStateToJson: JsObject = {
    Json.obj(
      "game" -> Json.obj(
        "aPlayer" -> JsString(GameStatus.activePlayer.get.name),
        "pPlayer" -> JsString(GameStatus.activePlayer.get.name),
        "movesLeft" -> JsNumber(GameStatus.currentPlayerActions),
        "hitchance" -> JsNumber(GameStatus.currentHitChance),
        "posATankX" -> JsNumber(GameStatus.activeTank.get.posC._1),
        "posATankY" -> JsNumber(GameStatus.activeTank.get.posC._2),
        "posPTankX" -> JsNumber(GameStatus.passiveTank.get.posC._1),
        "posPTankY" -> JsNumber(GameStatus.passiveTank.get.posC._2),
        "aTankHP" -> JsNumber(GameStatus.activeTank.get.hp),
        "pTankHP" -> JsNumber(GameStatus.passiveTank.get.hp),
        "aTankFacing" -> JsString(GameStatus.activeTank.get.facing),
        "pTankFacing" -> JsString(GameStatus.passiveTank.get.facing),
        "currentHS" -> JsNumber(GameStatus.currentHitChance),
        "movesLeft" -> JsBoolean(GameStatus.movesLeft)

      )

    )
  }

  def load: Unit = {
    controller
    val source: String = Source.fromFile("savegame.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val player1 = new Player((json \ "game" \ "aPlayer").get.toString())
    GameStatus.activePlayer = Option(player1)
    val player2 = new Player((json \ "game" \ "pPlayer").get.toString())
    GameStatus.activePlayer = Option(player2)
    GameStatus.currentPlayerActions = (json \ "game" \ "movesLeft").get.toString().toInt
    GameStatus.currentHitChance = (json \ "game" \ "hitchance").get.toString().toInt
    val tank1: TankModel = new TankModel((json \ "game" \ "aTankHP").get.toString().toInt,
      ((json \ "game" \ "posATankX").get.toString().toInt, (json \ "game" \ "posATankY").get.toString().toInt),
      (json \ "game" \ "aTankFacing").get.toString())
    val tank2: TankModel = new TankModel((json \ "game" \ "pTankHP").get.toString().toInt,
      ((json \ "game" \ "posPTankX").get.toString().toInt, (json \ "game" \ "posPTankY").get.toString().toInt),
      (json \ "game" \ "pTankFacing").get.toString().toString)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.currentHitChance = (json \ "game" \ "currentHS").get.toString().toInt
    GameStatus.movesLeft = (json \ "game" \ "movesLeft").get.toString().toBoolean
    controller.matchfield.marray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
      .containsThisTank = Option(tank1)
    controller.matchfield.marray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
      .containsThisTank = Option(tank2)
  }
}
