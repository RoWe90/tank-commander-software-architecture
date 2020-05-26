package tankcommander.view.gui

import javax.swing.ImageIcon
import tankcommander.controllerComponent.controllerBaseImpl.Controller

import scala.swing._
import scala.swing.event.ButtonClicked

class MainMenu(controller: Controller) extends Frame {
  //Main Menu
  title = "Tank Commander"
  menuBar = new MenuBar {
    contents += new Menu("File") {
      // contents += new MenuItem("New Game") {

      // }
      // contents += new MenuItem("Restart") {

      // }
      //  contents += new Separator()
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

  contents = new GridPanel(4, 1) {
    val start = new Button() {
      this.preferredSize = new Dimension(600, 300)
      this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/start_final_kleiner.png")
        .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
    }
    val name1 = new TextField("Player 1: Type your Name here") {
      this.preferredSize = new Dimension(600, 50)
    }
    val name2 = new TextField("Player 2: Type your Name here") {
      this.preferredSize = new Dimension(600, 50)
    }
    val chosenMap = new ComboBox(List("Map 1", "Map 2")) {
    }

    val textFields = new GridPanel(3, 1) {
      contents += name1
      contents += name2
      contents += chosenMap
    }
    start.reactions += {
      case ButtonClicked(start) => {
        val gameInterface = new GameFieldGUI(controller, name1.text, name2.text, map = chosenMap.selection.item)
        dispose()
      }
    }
    val exit = new Button() {
      this.preferredSize = new Dimension(600, 300)
      this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/exit_final_kleiner.png")
        .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))

    }
    exit.reactions += {
      case ButtonClicked(exit) => System.exit(0)
    }
    val label = new Label("Hauptmenü") {
      this.icon = (new ImageIcon(new ImageIcon("src/main/ressources/icons/TankCommanderLogo.png")
        .getImage().getScaledInstance(600, 300, java.awt.Image.SCALE_SMOOTH)))
    }
    contents += label
    contents += start
    contents += textFields
    contents += exit
  }


  size = new Dimension(600, 1000)
  centerOnScreen()
  visible = true

}
