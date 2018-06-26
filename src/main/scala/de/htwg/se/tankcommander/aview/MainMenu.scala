package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import javax.swing.ImageIcon

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

  contents = new GridPanel(2, 1) {
    contents += new Label("Hauptmenü") {
      this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/logo_cut.png")
        .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
    }
    contents += new GridPanel(2, 1) {
      val start = new Button() {
        this.preferredSize = new Dimension(600, 300)
        this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/start_final.png")
          .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
      }
      contents += Button("Start") {

        val gameInterface = new GameFieldGUI(controller)
        dispose()
      }
      val exit = new Button() {
        this.preferredSize = new Dimension(600, 300)
        this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/exit_final.png")
          .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))

      }
      contents += exit
    }
  }

  size = new Dimension(600, 900)
  centerOnScreen()
  visible = true

}
