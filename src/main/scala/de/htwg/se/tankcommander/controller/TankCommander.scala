package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander._
import de.htwg.se.tankcommander.aview.TUI

object TankCommander {

  var tui = new TUI
  var spielfeld = new GameField
  var gameState = new GameState

  def main(args: Array[String]): Unit = {
    var data = gameState.setUpGame() //returns: (tank1, tank2, player1, player2)
    var input: String = ""
    println("Hauptmenü")
    println("Start")
    println("Exit")
    do {

      input = scala.io.StdIn.readLine()
      if (input != "Exit") {
        spielfeld = tui.processInputLine(input, spielfeld, (data._1, data._2))
      }
    } while (input != "Exit")


  }
}
