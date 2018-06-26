package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import javax.swing.ImageIcon

import scala.swing._
import scala.swing.event.ButtonClicked

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

  val mainMenu = new GridPanel(3, 1) {
    contents += label
    contents += start
    contents += exit
  }

  val start = new Button() {
    this.preferredSize = new Dimension(600, 300)
    this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/start_final.png")
      .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
  }
  val exit = new Button() {
    this.preferredSize = new Dimension(600, 300)
    this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/exit_final.png")
      .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))

  }
  val label = new Label("Hauptmenü") {
    this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/logo_cut.png")
      .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
  }

  listenTo(start)
  listenTo(exit)

  reactions += {
    case ButtonClicked(`start`) => {
      val gameInterface = new GameFieldGUI(controller)
      dispose()
    }
    case ButtonClicked(`exit`) => exit(0)
  }


  size = new Dimension(600, 900)
  centerOnScreen()
  visible = true

}
