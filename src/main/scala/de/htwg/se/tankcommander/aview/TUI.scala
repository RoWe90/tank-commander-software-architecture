package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.Controller
import de.htwg.se.tankcommander.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {

    input match {
      case "Start" => print("Das Spiel startet, macht euch bereit")
        controller.createNewMap()
      case "Exit" =>

      case "up" =>


      case "down" =>


      case "left" =>


      case "right" =>


      case "use" =>

      case "shoot" =>

      case _ => {
        print("There is no such action")

      }
    }
  }

  override def update: Unit = print(controller.matchfieldToString)
}


