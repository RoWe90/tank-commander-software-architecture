package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.model._

class TUI {

  def processInputLine(input: String): Unit = {
    input match {
      case "Start" => print("Das Spiel startet, macht euch bereit")

      case "Exit" =>
      case "up" =>
      case "down" =>
      case "left" =>
      case "right" =>
      case "use" =>
      case "shoot"=>

      case _ => {
      print("There is no such action")
      }
    }
  }

}


