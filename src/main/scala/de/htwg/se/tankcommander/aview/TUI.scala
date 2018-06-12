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
        controller.moveTank(input)

      case "down" =>
        controller.moveTank(input)

      case "left" =>
        controller.moveTank(input)

      case "right" =>
        controller.moveTank(input)
      case "shoot" =>
        controller.shoot()
      case _ => {
        print("There is no such action")

      }
    }
  }

  override def update: Unit = print(controller.matchfieldToString)
}


