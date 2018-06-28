package de.htwg.se.tankcommander

import com.google.inject.Guice
import de.htwg.se.tankcommander.aview.{MainMenu, TUI}
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller

object TankCommander {
  val injector = Guice.createInjector(new TankCommanderModule)
  val controller = new Controller()
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
