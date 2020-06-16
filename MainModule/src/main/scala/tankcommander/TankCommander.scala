package tankcommander

import com.google.inject.{Guice, Injector}
import tankcommander.view.http.HttpServer
import tankcommander.view.tui.TUI
import tankcommander.view.gui.MainMenu
import tankcommander.controllerComponent.controllerBaseImpl.Controller

object TankCommander {
  val injector: Injector = Guice.createInjector(new TankCommanderModule)
  val controller = new Controller()
  val webserver = new HttpServer(controller)
  val tui = new TUI(controller)
//  val gui = new MainMenu(tank.controller)

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
