package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.model.Cell
import de.htwg.se.tankcommander.model.GameField


class GameFieldCreator {

  def fillField(): Unit ={
    GameField spielfeld = new GameField
    def fillField(): Unit = {
      var z = 0
      for (_ <- 0 to 10) {
        for (i <- 0 until Spielfeld.matchfieldarray.length) {
          Spielfeld.matchfieldarray(i)(z) = new Cell(i, z)
        }
        z += 1
      }
    }
  }

  def createMap(): Unit = {

  }

}
