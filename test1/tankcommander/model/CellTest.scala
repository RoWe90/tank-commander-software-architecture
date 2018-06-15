package de.htwg.se.tankcommander.model.model

import de.htwg.se.tankcommander.model.Cell
import org.scalatest.{FlatSpec, Matchers}

class CellTest extends FlatSpec with Matchers {
  "Cell" should "have an x and y coordinate" in {
    val cell = new Cell(1, 2)
    assert(cell.x === 1)
    assert(cell.y === 2)

  }
  it should
    "initialise with null in cellObstacle and containsThisTank" in {
  val cell = new Cell(1,2)
    assert(cell.cellobstacle === null)
    assert(cell.containsThisTank === null)
  }
}
