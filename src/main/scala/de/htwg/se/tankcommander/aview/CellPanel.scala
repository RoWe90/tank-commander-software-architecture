package de.htwg.se.tankcommander.aview

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.{CellChanged, Controller}

import scala.swing._

class CellPanel(row: Int, column: Int, controller: Controller) extends FlowPanel {

  val givenCellColor = new Color(200, 200, 255)
  val cellColor = new Color(224, 224, 255)
  val label =
    new Label {
      text = cellText(row, column)
      font = new Font("Verdana", 1, 36)
    }
  val cell = new BoxPanel(Orientation.Vertical) {
    contents += label
    preferredSize = new Dimension(51, 51)
    label.text = cellText(row, column)
    //background = if (controller.matchfield.marray(row)(column).cobstacle.isDefined) givenCellColor else cellColor
    border = Swing.BeveledBorder(Swing.Raised)
    // listenTo(mouse.clicks)
    // listenTo(controller)
    reactions += {
      case e: CellChanged => {
        label.text = cellText(row, column)
        repaint
      }
    }
  }

  def redraw = {
    contents.clear()
    label.text = cellText(row, column)
    contents += cell
    repaint
  }

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

  def myCell = controller.matchfield.marray(row)(column)


}
