package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.aview.{MainMenu, TUI}
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.GameField

object TankCommander {
  val controller = new Controller(new GameField)
  val tui = new TUI(controller)
  val gui = new MainMenu(controller)

  def main(args: Array[String]): Unit = {
    var input: String = ""
    print("Hauptmenü\nStart\nExit\n")

    do {
      input = scala.io.StdIn.readLine()
      if (input != "Exit") {
        tui.processInputLine(input)
      }
    } while (input != "Exit")
  }
}
