package tankcommander.controllerComponent.fileIoComponent.fileIOXmlImpl

import tankModelComponent.TankModel
import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.controllerComponent.fileIoComponent.FileIOInterface
import tankcommander.gameState.GameStatus
import tankcommander.model.girdComponent.GameFieldInterface
import tankcommander.model.girdComponent.gridBaseImpl.Cell

import scala.xml.{Elem, PrettyPrinter, XML}

class FileIO() extends FileIOInterface {
  override def save(): Unit = saveString()

  def saveXML(gamefield: GameFieldInterface): Unit = {
    XML.save("src/main/ressources/savegame.xml", gameStateToXML())
  }

  def saveString(): Unit = {
    import java.io._
    val file = new File("src/main/ressources/savegame.xml")
    file.createNewFile()
    val pw = new PrintWriter(file)
    val prettyPrinter = new PrettyPrinter(120, 4)
    val xml = prettyPrinter.format(gameStateToXML())
    pw.write(xml)
    pw.close()
  }

  //noinspection ScalaStyle
  def gameStateToXML(): Elem = {
    <game>
      <aPlayer>
        {GameStatus.activePlayer.get}
      </aPlayer>
      <pPlayer>
        {GameStatus.passivePlayer.get}
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
    GameStatus.activePlayer = Some((file \\ "game" \\ "aPlayer").text)
    GameStatus.passivePlayer = Some((file \\ "game" \\ "pPlayer").text)
    GameStatus.currentPlayerActions = (file \\ "game" \\ "movesCount").text.replaceAll(" ", "").toInt
    GameStatus.currentHitChance = (file \\ "game" \ "hitchance").text.replaceAll(" ", "").toInt
    val tank1: TankModel = TankModel(
      hp = (file \\ "game" \ "aTankHP").text.replaceAll(" ", "").toInt,
      posC = ((file \\ "game" \ "posATankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posATankY").text.replaceAll(" ", "").toInt),
      facing = (file \\ "game" \ "aTankFacing").text.replaceAll(" ", ""))
    val tank2: TankModel = TankModel(
      hp = (file \\ "game" \ "pTankHP").text.replaceAll(" ", "").toInt,
      posC = ((file \\ "game" \ "posPTankX").text.replaceAll(" ", "").toInt,
        (file \\ "game" \ "posPTankY").text.replaceAll(" ", "").toInt),
      facing = (file \\ "game" \ "pTankFacing").text.replaceAll(" ", ""))
    GameStatus.activeTank = Some(tank1)
    GameStatus.passiveTank = Some(tank2)
    GameStatus.movesLeft = (file \\ "game" \ "movesLeft").text.replaceAll(" ", "").toBoolean
    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      GameStatus.activeTank.get.posC._1, controller.matchfield.mvector(GameStatus.activeTank.get.posC._1).updated(
        GameStatus.activeTank.get.posC._2, Cell(GameStatus.activeTank.get.posC,
          controller.matchfield.mvector(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._1).cobstacle, Some(GameStatus.activeTank.get)))))

    controller.matchfield = controller.matchfield.update(controller.matchfield.mvector.updated(
      GameStatus.passiveTank.get.posC._1, controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1).updated(
        GameStatus.passiveTank.get.posC._2, Cell(GameStatus.passiveTank.get.posC,
          controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._1).cobstacle, Some(GameStatus.passiveTank.get)))))
    //    controller.matchfield.mvector(GameStatus.activeTank.get.posC._1)(GameStatus.activeTank.get.posC._2)
    //      .containsThisTank = Option(tank1)
    //    controller.matchfield.mvector(GameStatus.passiveTank.get.posC._1)(GameStatus.passiveTank.get.posC._2)
    //      .containsThisTank = Option(tank2)
  }
}
