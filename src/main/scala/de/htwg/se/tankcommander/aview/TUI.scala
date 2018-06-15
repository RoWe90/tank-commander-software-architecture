package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.{Controller, GameStatus}
import de.htwg.se.tankcommander.util.Observer

//noinspection ScalaStyle
class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {
    input.toLowerCase match {
      case "start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.createNewMap()
        controller.setUpGame()
      case "exit" =>
      case "up" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "down" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "left" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "right" if controller.checkIfPlayerHasMovesLeft() => controller.moveTank(input)
      case "shoot" if controller.checkIfPlayerHasMovesLeft() => controller.shoot()
      case "end turn" => controller.endTurnChangeActivePlayer()
      case _ if input.contains("turn") =>
        if (input.contains("up")) controller.turnTank("up")
        else if (input.contains("down")) controller.turnTank("down")
        else if (input.contains("right")) controller.turnTank("right")
        else if (input.contains("left")) controller.turnTank("left")
        else print("cant turn tank this way\n")
      case _ =>
        print("")
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


