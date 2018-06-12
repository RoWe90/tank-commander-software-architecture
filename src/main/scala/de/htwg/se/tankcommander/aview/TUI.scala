package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.model._

class TUI {

  def processInputLine(input: String, spielfeld: GameField, tanks: (TankModel, TankModel), activeTank: TankModel,
                       activePlayer: Player): GameField = {

    input match {
      case "Start" => print("Das Spiel startet, macht euch bereit")
        spielfeld.createMap(tanks)
        print(spielfeld.toString)
        spielfeld
      case "Exit" =>
        spielfeld
      case "up" =>
        spielfeld.moveUP(activeTank)
        spielfeld
      case "down" =>
        spielfeld.moveDown(activeTank)
        spielfeld
      case "left" =>
        spielfeld.moveLeft(activeTank)
        spielfeld
      case "right" =>
        spielfeld.moveRight(activeTank)
        spielfeld
      case "use" =>
        spielfeld
      case "shoot" =>
        spielfeld
      case _ => {
        print("There is no such action")
        spielfeld
      }
    }
  }

}


