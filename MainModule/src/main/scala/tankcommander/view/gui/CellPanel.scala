package tankcommander.view.gui

import javax.swing.ImageIcon
import tankcommander.controllerComponent.controllerBaseImpl.Controller
import tankcommander.util.Observer

import scala.swing._

//Individual Cells
class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
  controller.add(this)
  val label: Label = new Label {
    text = cellText(row, column)
    font = new Font("Verdana", 1, 36)
  }
  // val icon = new Label{
  //   icon = cellIcon(row,column)
  // }

  val cell: BoxPanel = new BoxPanel(Orientation.Vertical) {
    contents += label
    contents += new Label {
      new ImageIcon("src/main/ressources/icons/tank.png")
    }
    preferredSize = new Dimension(51, 51)
    label.text = cellText(row, column)
    border = Swing.BeveledBorder(Swing.Raised)
  }

  // def reload = {
  //   contents.clear()
  //   label.text = cellText(row, column)
  //   contents += label
  //   repaint
  // }

  def cellIcon(row: Int, col: Int): Label = if (myCell.cobstacle.isDefined) {
    if (myCell.containsThisTank.isDefined) {
      new Label {
        new ImageIcon("src/main/ressources/icons/tank.png")
      }
    } else {
      new Label {
        new ImageIcon(myCell.cobstacle.get.imagePath)
      }
    }
  } else {
    if (myCell.containsThisTank.isDefined) {
      new Label {
        new ImageIcon("src/main/ressources/icons/tank.png")
      }
    } else {
      new Label {
        new ImageIcon("src/main/ressources/icons/grass.png")
      }

    }
  }

  override def update(): Unit = {
    this.label.text = cellText(row, column)
    repaint
  }

  def cellText(row: Int, col: Int): String = if (myCell.cobstacle.isDefined) {
    if (myCell.containsThisTank.isDefined) {
      "T"
    } else {
      myCell.cobstacle.get.shortName
    }
  } else {
    if (myCell.containsThisTank.isDefined) {
      "T"
    } else {
      "o"
    }
  }

  private def myCell = controller.matchfield.mvector(row)(column)
}
