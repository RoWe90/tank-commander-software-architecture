package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.fileIOXmlImpl

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent.FileIOInterface
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import de.htwg.se.tankcommander.model.playerComponent.Player
import scala.xml.{NodeSeq, PrettyPrinter}

class FileIO() extends FileIOInterface {
  override def save: Unit = {
    import java.io._
    val pw = new PrintWriter(new File("savegame.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(GameStateToXML)
    pw.write(xml)
    pw.close
  }

  //noinspection ScalaStyle
  def GameStateToXML() = {
    <game>
      <aPlayer>
        {GameStatus.activePlayer.get.toString}
      </aPlayer>

      <pPlayer>
        {GameStatus.passivePlayer.get.toString}
      </pPlayer>

      <movesCount>
        {GameStatus.currentPlayerActions.toString}
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
    val file = scala.xml.XML.loadFile("savegame.xml")
    val player1 = new Player((file \\ "game" \ "aPlayer").text)
    GameStatus.activePlayer = Option(player1)
    val player2 = new Player((file \\ "game" \ "pPlayer").text)
    GameStatus.activePlayer = Option(player2)
    GameStatus.currentPlayerActions = ((file \\ "game" \ "movesCount").text.toInt)
    GameStatus.currentHitChance = (file \\ "game" \ "hitchance").text.toInt
    val tank1: TankModel = new TankModel((file \\ "game" \ "aTankHP").text.toInt,
      ((file \\ "game" \ "posATankX").text.toInt, (file \\ "game" \ "posATankY").text.toInt),
      (file \\ "game" \ "aTankFacing").text)
    val tank2: TankModel = new TankModel((file \\ "game" \ "pTankHP").text.toInt,
      ((file \\ "game" \ "posPTankX").text.toInt, (file \\ "game" \ "posPTankY").text.toInt),
      (file \\ "game" \ "pTankFacing").text)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.currentHitChance = (file \\ "game" \ "currentHS").text.toInt
    GameStatus.movesLeft = (file \\ "game" \ "movesLeft").text.toBoolean
    controller.matchfield.marray(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
      .containsThisTank = Option(tank1)
    controller.matchfield.marray(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
      .containsThisTank = Option(tank2)
  }
}
