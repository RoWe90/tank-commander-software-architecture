package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander._
import de.htwg.se.tankcommander.aview.TUI

object TankCommander {
  def main(args: Array[String]): Unit = {
    var tui = new TUI;
    var spielfeld = new GameField
    var gameState = new GameState(spielfeld)
    gameState.setUpGame()
    tui.processInputLine(scala.io.StdIn.readLine())

  }
}
