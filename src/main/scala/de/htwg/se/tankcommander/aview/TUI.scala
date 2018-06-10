package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.model.GameField

class TUI {

  def processInputLine(input: String): Unit = {
    input match {
      case "Start" =>   fillField()
        setUpGame()
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


