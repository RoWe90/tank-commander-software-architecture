package de.htwg.se.tankcommander.controller.controllerComponent.fileIoJsonImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import play.api.libs.json._
import scala.io.Source

object SaveGame {
  def save: Unit = {
    import java.io._
    val pw = new PrintWriter(new File("savegame.json"))
    //pw.write()
    pw.close
  }

  def load: Unit = {
    val source: String = Source.fromFile("grid.json").getLines.mkString
    val json: JsValue = Json.parse(source)
    val player1 = new Player((json \ "game" \ "aPlayer").get.toString())
    GameStatus.activePlayer = Option(player1)
    val player2 = new Player((json \ "game" \ "pPlayer").get.toString())
    GameStatus.activePlayer = Option(player2)
    GameStatus.currentPlayerActions = (json \ "game" \ "movesLeft").get.toString().toInt
    GameStatus.currentHitChance = (json \ "game" \ "hitchance").get.toString().toInt
  }

  def GameStateToJson: Unit = {
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
        "pTankFacing" -> JsString(GameStatus.passiveTank.get.facing)
      )

    )
  }
}
