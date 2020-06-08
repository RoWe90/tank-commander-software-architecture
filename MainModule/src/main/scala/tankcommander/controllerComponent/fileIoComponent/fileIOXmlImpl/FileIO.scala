package tankcommander.controllerComponent.fileIoComponent.fileIOXmlImpl

import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.controllerComponent.fileIoComponent.FileIOInterface
import tankcommander.gameState.GameStatus
import tankcommander.model.girdComponent.GameFieldInterface
import tankcommander.model.girdComponent.gridBaseImpl.Cell
import tankcommander.util.AttributeHandler

import scala.xml.{Elem, PrettyPrinter, XML}

class FileIO() extends FileIOInterface {
  val attributeHandler = AttributeHandler()

  override def save(): Unit = saveString()

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

  def saveXML(gamefield: GameFieldInterface): Unit = {
    XML.save("src/main/ressources/savegame.xml", gameStateToXML())
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
        {attributeHandler.getPosC(GameStatus.activeTank.get)._1}
      </posATankX>
      <posATankY>
        {attributeHandler.getPosC(GameStatus.activeTank.get)._2}
      </posATankY>
      <posPTankX>
        {attributeHandler.getPosC(GameStatus.passiveTank.get)._1}
      </posPTankX>
      <posPTankY>
        {attributeHandler.getPosC(GameStatus.passiveTank.get)._2}
      </posPTankY>
      <aTankHP>
        {attributeHandler.getAttribute(GameStatus.activeTank.get, "hp")}
      </aTankHP>
      <pTankHP>
        {attributeHandler.getAttribute(GameStatus.passiveTank.get, "hp")}
      </pTankHP>
      <aTankFacing>
        {attributeHandler.getAttribute(GameStatus.activeTank.get, "facing")}
      </aTankFacing>
      <pTankFacing>
        {attributeHandler.getAttribute(GameStatus.passiveTank.get, "facing")}
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
    attributeHandler.setAttribute(1, "hp", (file \\ "game" \ "aTankHP").text.replaceAll(" ", ""))
    attributeHandler.setAttribute(1, "posC", (file \\ "game" \ "posATankX").text.replaceAll(" ", "") + "_" + (file \\ "game" \ "posATankY").text.replaceAll(" ", ""))
    attributeHandler.setAttribute(1, "facing", (file \\ "game" \ "aTankFacing").text.replaceAll(" ", ""))
    attributeHandler.setAttribute(2, "hp", (file \\ "game" \ "pTankHP").text.replaceAll(" ", ""))
    attributeHandler.setAttribute(2, "posC", (file \\ "game" \ "posPTankX").text.replaceAll(" ", "") + "_" + (file \\ "game" \ "posPTankY").text.replaceAll(" ", ""))
    attributeHandler.setAttribute(2, "facing", (file \\ "game" \ "pTankFacing").text.replaceAll(" ", ""))
    GameStatus.activeTank = Some(1)
    GameStatus.passiveTank = Some(2)
    GameStatus.movesLeft = (file \\ "game" \ "movesLeft").text.replaceAll(" ", "").toBoolean
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
