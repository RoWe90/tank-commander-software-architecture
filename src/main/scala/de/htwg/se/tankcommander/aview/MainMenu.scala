package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller

import scala.swing._

class MainMenu(controller: Controller) extends Frame {

  title = "Tank Commander"
  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem("New Game") {

      }
      contents += new MenuItem("Restart") {

      }
      contents += new Separator()
      contents += new MenuItem("Load") {

      }
      contents += new MenuItem("Save") {

      }
      contents += new Separator()
      contents += new MenuItem(Action("Exit") {
        sys.exit(0)
      })
    }
  }

  contents = new GridPanel(3, 1) {
    contents += new Label("Hauptmenü")
    contents += new GridPanel(2, 1) {
      contents += Button("Start") {
        val gameInterface = new GameFieldGUI(controller)
        dispose()
      }
      contents += Button("Exit")(
        sys.exit(0)
      )
    }
  }

  size = new Dimension(600, 900)
  centerOnScreen()
  visible = true

}
