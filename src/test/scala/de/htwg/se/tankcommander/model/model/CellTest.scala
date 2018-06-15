package de.htwg.se.tankcommander.model.model

import org.scalatest.{FlatSpec, Matchers}

class CellTest extends FlatSpec with Matchers {
  "Cell" should "have an x and y coordinate" in {
    val cell = new Cell
    assert(cellTest._1 == 1)

  }
}
