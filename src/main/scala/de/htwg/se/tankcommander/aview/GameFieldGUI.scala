package de.htwg.se.tankcommander.aview

import java.awt.Dimension

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.util.Observer
import javax.swing.ImageIcon

import scala.swing.Swing.LineBorder
import scala.swing._
import scala.swing.event.ButtonClicked

class GameFieldGUI(controller: Controller) extends Frame with Observer {
  controller.add(this)
  var cells = Array.ofDim[CellPanel](controller.matchfield.gridsX, controller.matchfield.gridsY)
  val statusLine = new TextArea()
  val messages = new TextArea("Welcome to the Game. \n" +
    "Use the Buttons on the right to control your tank.")
  paintGameField(controller)
  controller.setUpGame()

  val controls = new GridPanel(5, 1) {
    val up = new Button("Up") {
      this.preferredSize = (new Dimension(100, 120))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\up.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += up
    val down = new Button("Down") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\down.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += down
    val left = new Button("Left") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\left.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += left
    val right = new Button("Right") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\right.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += right
    val shoot = new Button("Shoot!") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\explosion.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += shoot
    val end_turn = new Button("End Turn") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\sandclock_take.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += end_turn
    val give_up = new Button("Give up") {
      this.preferredSize = (new Dimension(50, 50))
      this.icon = (new ImageIcon(new ImageIcon("C:\\Users\\Robin\\Desktop\\test\\white_flag.png")
        .getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)))
      this.borderPainted = (true)
    }
    contents += give_up
    up.reactions += {
      case ButtonClicked(up) => if (controller.checkIfPlayerHasMovesLeft())
        controller.move("up")
      else {
        messages.append("\nNo more Moves left, end your turn!")
      }
    }
    down.reactions += {
      case ButtonClicked(up) => if (controller.checkIfPlayerHasMovesLeft())
        controller.move("down")
      else {
        messages.append("\nNo more Moves left, end your turn!")
      }
    }
    left.reactions += {
      case ButtonClicked(up) =>
        if (controller.checkIfPlayerHasMovesLeft())
          controller.move("left")
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    right.reactions += {
      case ButtonClicked(up) =>
        if (controller.checkIfPlayerHasMovesLeft())
          controller.move("right")
        else {
          messages.append("\nNo more Moves left, end your turn!")
        }
    }
    shoot.reactions += {
      case ButtonClicked(shoot) => if (controller.checkIfPlayerHasMovesLeft())
        controller.shoot()
      else {
        messages.append("\nNo more Moves left, end your turn!")
      }
    }
    end_turn.reactions += {
      case ButtonClicked(end_turn) => controller.endTurnChangeActivePlayer()
        messages.append("\nPlease change seats.")
    }
    give_up.reactions += {
      case ButtonClicked(give_up) => GameStatus.endGame()
        messages.append("\n" + GameStatus.activePlayer.toString + " gave up, \n" + GameStatus.passivePlayer.toString + " has won!")
    }

  }

  title = "Tank Commander"
  menuBar = new MenuBar {
    contents += new Menu("File") {
      contents += new MenuItem("New Game") {
      }
      contents += new MenuItem("Restart") {
      }
      contents += new Separator()
      contents += new MenuItem(Action("Load") {
        controller.load()
      })
      contents += new MenuItem(Action("Save") {
        controller.save()
      })
      contents += new Separator()
      contents += new MenuItem(Action("Undo") {
        controller.undo()
      })
      contents += new MenuItem(Action("Redo") {
        controller.redo()
      })
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
    add(messages, BorderPanel.Position.West)
  }


  def paintWindow(controller: Controller): BorderPanel = {
    import BorderPanel.Position._
    val mainFrame = new BorderPanel {
      val gameArea = paintGameField(controller)
      layout += gameArea -> Center
      layout += statusLine -> South

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
          //listenTo(cellPanel)
        }
    }
    gameField
  }

  override def update: Unit = {
    redraw()
  }

  def redraw(): Unit = {
    for {
      column <- 0 until controller.matchfield.gridsY
    }
      for {
        row <- 0 until controller.matchfield.gridsX
      } {
        cells(row)(column)
      }
    statusLine.text = "aktiver Spieler: " + GameStatus.activePlayer.get + " Hitpoints: " +
      GameStatus.activeTank.get.hp + "\n" + "MovesLeft: " + GameStatus.currentPlayerActions + "\n" +
      "passiver Spieler: " + GameStatus.passivePlayer.get + " Hitpoints: " +
      GameStatus.passiveTank.get.hp + "\n"
    repaint
  }
}
