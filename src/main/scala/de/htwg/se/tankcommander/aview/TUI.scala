package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.Controller
import de.htwg.se.tankcommander.util.Observer

class TUI(controller: Controller) extends Observer {
  controller.add(this)

  def processInputLine(input: String): Unit = {

    input match {
      case "Start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.createNewMap()
        controller.setUpGame()
      case "Exit" =>

      case "up" =>
        controller.moveTank(input)
        print(controller.getCurrentHitChanceOfGamestatus + "\n")

      case "down" =>
        controller.moveTank(input)
        print(controller.getCurrentHitChanceOfGamestatus + "\n")

      case "left" =>
        controller.moveTank(input)
        print(controller.getCurrentHitChanceOfGamestatus + "\n")

      case "right" =>
        controller.moveTank(input)
        print(controller.getCurrentHitChanceOfGamestatus + "\n")
      case "shoot" =>
        controller.shoot()
      case _ => {
        print("There is no such action")

      }
    }
  }

  override def update: Unit = print(controller.matchfieldToString)
}


