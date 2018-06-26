package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIOXmlImpl

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{GameField, TankModel}
import de.htwg.se.tankcommander.model.playerComponent.Player

import scala.xml.{NodeSeq, PrettyPrinter}
import scala.xml.XML

class FileIO() extends FileIOInterface {
  def saveString: Unit = {
    import java.io._
    val pw = new PrintWriter(new File("savegame.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXML())
    pw.write(xml)
    pw.close()
  }

  def saveXML(gamefield: GameFieldInterface): Unit = {
    XML.save("savegame.xml", gameStateToXML())
  }

  override def save(): Unit = saveString

  //noinspection ScalaStyle
  def gameStateToXML() = {
    <game>
      <aPlayer>
        {GameStatus.activePlayer.get.toString}
      </aPlayer>
      <pPlayer>
        {GameStatus.passivePlayer.get.toString}
      </pPlayer>
      <movesCount>
        {GameStatus.currentPlayerActions}
      </movesCount>
      <hitchance>
        {GameStatus.currentHitChance}
      </hitchance>
      <posATankX>
        {GameStatus.activeTank.get.posC._1}
      </posATankX>
      <posATankY>
        {GameStatus.activeTank.get.posC._2}
      </posATankY>
      <posPTankX>
        {GameStatus.passiveTank.get.posC._1}
      </posPTankX>
      <posPTankY>
        {GameStatus.passiveTank.get.posC._2}
      </posPTankY>
      <aTankHP>
        {GameStatus.activeTank.get.hp}
      </aTankHP>
      <pTankHP>
        {GameStatus.passiveTank.get.hp}
      </pTankHP>
      <aTankFacing>
        {GameStatus.activeTank.get.facing}
      </aTankFacing>
      <pTankFacing>
        {GameStatus.passiveTank.get.facing}
      </pTankFacing>
      <currentHS>
        {GameStatus.currentHitChance}
      </currentHS>
      <movesLeft>
        {GameStatus.movesLeft}
      </movesLeft>
    </game>
  }

  override def load(controller: Controller): Unit = {
    val file = XML.loadFile("savegame.xml")
    GameStatus.activePlayer = Option(new Player((file \\ "game" \\ "aPlayer").text))
    GameStatus.passivePlayer = Option(new Player((file \\ "game" \\ "pPlayer").text))
    GameStatus.currentPlayerActions = (file \\ "game" \\ "movesCount").text.replaceAll(" ", "").toInt
    GameStatus.currentHitChance = (file \\ "game" \ "hitchance").text.replaceAll(" ", "").toInt
    val tank1: TankModel = new TankModel((file \\ "game" \ "aTankHP").text.replaceAll(" ", "").toInt,
      ((file \\ "game" \ "posATankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posATankY").text.replaceAll(" ", "").toInt),
      (file \\ "game" \ "aTankFacing").text.replaceAll(" ", ""))
    val tank2: TankModel = new TankModel((file \\ "game" \ "pTankHP").text.replaceAll(" ", "").toInt,
      ((file \\ "game" \ "posPTankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posPTankY").text.replaceAll(" ", "").toInt),
      (file \\ "game" \ "pTankFacing").text.replaceAll(" ", ""))
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.movesLeft = (file \\ "game" \ "movesLeft").text.replaceAll(" ", "").toBoolean
    controller.matchfield.marray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
      .containsThisTank = Option(tank1)
    controller.matchfield.marray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
      .containsThisTank = Option(tank2)
  }
}
