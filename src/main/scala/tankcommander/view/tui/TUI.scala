package tankcommander.view.tui

import gamestate.GameStatus
import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.setUpGame()
      case "exit" =>
      case "end turn" => controller.endTurnChangeActivePlayer()
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "save" => controller.save()
      case "load" => controller.load()
      case _ => if (!controller.checkIfPlayerHasMovesLeft())
        println("no turns left")
      case _ =>
    }
    if (controller.checkIfPlayerHasMovesLeft()) {
      input.toLowerCase match {
        case "up" => controller.move(input)
        case "down" => controller.move(input)
        case "left" => controller.move(input)
        case "right" => controller.move(input)
        case "shoot" => controller.shoot()
        case _ =>
      }
    }
  }

  override def update(): Unit = {
    print(controller.matchfieldToString)
    print("aktiver Spieler: " + GameStatus.activePlayer.get + " Hitpoints: " +
      GameStatus.activeTank.get.hp + "\n" + "MovesLeft: " + GameStatus.currentPlayerActions + "\n" +
      "passiver Spieler: " + GameStatus.passivePlayer.get + " Hitpoints: " +
      GameStatus.passiveTank.get.hp + "\n")
  }
}


