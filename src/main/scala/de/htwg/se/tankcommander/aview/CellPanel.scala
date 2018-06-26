package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.util.Observer

import scala.swing._

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel with Observer {
  controller.add(this)
  val label = new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 36)
    }
  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
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

  private def myCell = controller.matchfield.marray(row)(column)

  override def update: Unit = {
    this.label.text = cellText(row, column)
    repaint
  }
}
