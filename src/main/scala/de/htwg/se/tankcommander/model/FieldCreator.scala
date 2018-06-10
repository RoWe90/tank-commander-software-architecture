package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.model.Cell
import de.htwg.se.tankcommander.model.Field


class FieldCreator {

  def fillField(Spielfeld:Field): Unit ={
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
