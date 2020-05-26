package tankcommander

import com.google.inject.{Guice, Injector}
import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.view.gui.MainMenu
import tankcommander.view.tui.TUI

object TankCommander {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
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
