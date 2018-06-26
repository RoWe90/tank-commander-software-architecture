package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.util.Observer
import javax.swing.ImageIcon

import scala.swing._

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
  controller.add(this)
  val label = new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 36)
    }
  // val icon = new Label{
  //   icon = cellIcon(row,column)
  // }

  val cell = new BoxPanel(Orientation.Vertical) {
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

  def cellText(row: Int, col: Int) = if (myCell.cobstacle.isDefined) {
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

  private def myCell = controller.matchfield.marray(row)(column)

  override def update: Unit = {
    this.label.text = cellText(row, column)
    repaint
  }
}
