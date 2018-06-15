package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.aview.TUI
import de.htwg.se.tankcommander.controller.Controller
import de.htwg.se.tankcommander.model.GameField

object TankCommander {
  val controller = new Controller(new GameField)
  val tui = new TUI(controller)

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
