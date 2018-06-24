package de.htwg.se.tankcommander.aview

import java.awt.Dimension
import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.util.Observer
import scala.swing.Swing.LineBorder
import scala.swing._

class GameFieldGUI(controller: Controller) extends Frame with Observer {
  val controls = new GridPanel(5, 1) {
    contents += new Button("Up") {
      controller.move("up")
    }
    contents += new Button("Down") {
      controller.move("down")
    }
    contents += new Button("Left") {
      controller.move("left")
    }
    contents += new Button("Right") {
      controller.move("right")
    }
    contents += new Button("End Turn") {
      controller.endTurnChangeActivePlayer()
    }
  }
  controller.add(this)
  controller.setUpGame()
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
  visible = true
  centerOnScreen()
  val dimension = new Dimension(1000, 1000)
  contents = new BorderPanel {
    add(paintWindow(controller), BorderPanel.Position.Center)
    add(controls, BorderPanel.Position.East)
  }
  var cells = Array.ofDim[CellPanel](controller.matchfield.gridsX, controller.matchfield.gridsY)

  def paintWindow(controller: Controller): BorderPanel = {
    import BorderPanel.Position._
    val mainFrame = new BorderPanel {
      layout += paintGameField(controller) -> Center
      layout += new TextArea("aktiver Spieler: " + GameStatus.activePlayer.get + " Hitpoints: " +
        GameStatus.activeTank.get.hp + "\n" + "MovesLeft: " + GameStatus.currentPlayerActions + "\n" +
        "passiver Spieler: " + GameStatus.passivePlayer.get + " Hitpoints: " +
        GameStatus.passiveTank.get.hp + "\n"
      ) -> South
    }
    mainFrame
  }

  def paintGameField(controller: Controller): GridPanel = {
    val gameField = new GridPanel(controller.matchfield.gridsX, controller.matchfield.gridsY) {
      border = LineBorder(java.awt.Color.BLACK, 2)
      for {
        column <- 0 until controller.matchfield.gridsY
      }
        for {
          row <- 0 until controller.matchfield.gridsX
        } {
          val cellPanel = new CellPanel(row, column, controller)
          cells(row)(column) = cellPanel
          contents += cellPanel.cell
          listenTo(cellPanel)
        }
    }
    gameField
  }

  override def update: Unit = {
    redraw
  }

  def redraw(): Unit = {
    for {
      row <- 0 until controller.matchfield.gridsX
      column <- 0 until controller.matchfield.gridsY
    } cells(row)(column).redraw
    repaint
  }
}
