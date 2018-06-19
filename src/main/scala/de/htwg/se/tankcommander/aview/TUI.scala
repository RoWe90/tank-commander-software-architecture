package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.{Controller, GameStatus}
import de.htwg.se.tankcommander.util.Observer

//noinspection ScalaStyle
class TUI(controller: Controller) extends Observer {
  controller.add(this)
  var commandlist = List("start", "up", "down", "right", "left", "exit", "shoot", "end turn", "undo", "redo")

  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.setUpGame()
      case "exit" =>
      case "end turn" => controller.endTurnChangeActivePlayer()
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case _ =>
    }
    if (controller.checkIfPlayerHasMovesLeft()) {
      input.toLowerCase match {
        case "up" => controller.move(input)
        case "down" => controller.move(input)
        case "left" => controller.move(input)
        case "right" => controller.move(input)
        case "shoot" => controller.shootC()
        case _ =>
      }
    }
  }

  override def update: Unit = {
    print(controller.matchfieldToString)
    print("aktiver Spieler: " + GameStatus.activePlayer.get + " Hitpoints: " +
      GameStatus.activeTank.get.hp + "\n" + "MovesLeft: " + GameStatus.currentPlayerActions + "\n")
    print("passiver Spieler: " + GameStatus.passivePlayer.get + " Hitpoints: " +
      GameStatus.passiveTank.get.hp + "\n")
  }
}


