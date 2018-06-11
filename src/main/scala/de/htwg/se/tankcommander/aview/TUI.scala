package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.model._

class TUI {

  def processInputLine(input: String, spielfeld: GameField, tanks: (TankModel, TankModel)): GameField = {

    input match {
      case "Start" => print("Das Spiel startet, macht euch bereit")

        spielfeld.fillField()
        spielfeld.createMap(tanks)
        print(spielfeld.toString)
        println("")
        spielfeld
      case "Exit" =>
        spielfeld
      case "up" =>
        spielfeld.moveUP(tanks._1)
        spielfeld
      case "down" =>
        spielfeld.moveDown(tanks._1)
        spielfeld
      case "left" =>
        spielfeld.moveLeft(tanks._1)
        spielfeld
      case "right" =>
        spielfeld.moveRight(tanks._1)
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


