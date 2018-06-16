package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.{Controller, GameStatus}
import de.htwg.se.tankcommander.util.Observer

//noinspection ScalaStyle
class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.createNewGameField()
        controller.setUpGame()
      case "exit" =>

      case "up" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "down" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "left" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "right" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "shoot" if controller.checkIfPlayerHasMovesLeft() => controller.shootC()
      case "end turn" => controller.endTurnChangeActivePlayer()
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case _ =>
        print("Not a viable Command")
    }
  }

  override def update: Unit = {
    print(controller.matchfieldToString)
    print("aktiver Player: " + GameStatus.activePlayer.get + " TankHitpoints: " +
      GameStatus.activeTank.get.healthpoints + "\n" + " MovesLeft: " + GameStatus.currentPlayerActions + " TankFacing: "
      + GameStatus.activeTank.get.facing + "\n")
    print("passiver Player: " + GameStatus.passivePlayer.get + " TankHitpoints: " +
      GameStatus.passiveTank.get.healthpoints + "\n")
  }
}


