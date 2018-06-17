package de.htwg.se.tankcommander.model

import org.scalatest._

class CellTest extends FlatSpec with Matchers {
  "Cell" should "have an x and y coordinate" in {
    val cell = new Cell(Position(1, 0))
    assert(cell.x === 1)

  }
}
