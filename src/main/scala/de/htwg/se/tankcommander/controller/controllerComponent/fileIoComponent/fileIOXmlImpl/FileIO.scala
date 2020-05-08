package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIOXmlImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{Cell, TankModel}
import de.htwg.se.tankcommander.model.playerComponent.Player

import scala.xml.{PrettyPrinter, XML}

class FileIO() extends FileIOInterface {
  def saveString: Unit = {
    import java.io._
    val file = new File("src/main/ressources/savegame.xml")
    file.createNewFile()
    val pw = new PrintWriter(file)
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXML())
    pw.write(xml)
    pw.close()
  }

  def saveXML(gamefield: GameFieldInterface): Unit = {
    XML.save("src/main/ressources/savegame.xml", gameStateToXML())
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
    val file = XML.loadFile("src/main/ressources/savegame.xml")
    GameStatus.activePlayer = Option(new Player((file \\ "game" \\ "aPlayer").text))
    GameStatus.passivePlayer = Option(new Player((file \\ "game" \\ "pPlayer").text))
    GameStatus.currentPlayerActions = (file \\ "game" \\ "movesCount").text.replaceAll(" ", "").toInt
    GameStatus.currentHitChance = (file \\ "game" \ "hitchance").text.replaceAll(" ", "").toInt
    val tank1: TankModel = new TankModel(
      hp = (file \\ "game" \ "aTankHP").text.replaceAll(" ", "").toInt,
      posC = ((file \\ "game" \ "posATankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posATankY").text.replaceAll(" ", "").toInt),
      facing = (file \\ "game" \ "aTankFacing").text.replaceAll(" ", ""))
    val tank2: TankModel = new TankModel(
      hp = (file \\ "game" \ "pTankHP").text.replaceAll(" ", "").toInt,
      posC = ((file \\ "game" \ "posPTankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posPTankY").text.replaceAll(" ", "").toInt),
      facing = (file \\ "game" \ "pTankFacing").text.replaceAll(" ", ""))
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.movesLeft = (file \\ "game" \ "movesLeft").text.replaceAll(" ", "").toBoolean
    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      GameStatus.activeTank.get.posC._1, controller.matchfield.mvector(GameStatus.activeTank.get.posC._1).updated(
        GameStatus.activeTank.get.posC._2, Cell(GameStatus.activeTank.get.posC,
          controller.matchfield.mvector(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._1).cobstacle, Option(GameStatus.activeTank.get)))))

    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      GameStatus.passiveTank.get.posC._1, controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1).updated(
        GameStatus.passiveTank.get.posC._2, Cell(GameStatus.passiveTank.get.posC,
          controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._1).cobstacle, Option(GameStatus.passiveTank.get)))))
    //    controller.matchfield.mvector(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
    //      .containsThisTank = Option(tank1)
    //    controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
    //      .containsThisTank = Option(tank2)
  }
}
