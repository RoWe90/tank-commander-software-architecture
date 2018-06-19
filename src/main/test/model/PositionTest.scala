package model

import de.htwg.se.tankcommander.model.Position
import org.scalatest.{FlatSpec, Matchers}

class PositionTest extends FlatSpec with Matchers {

  "A Position" should "have x and y coordinates" in {

    val pos = Position(1, 2)

    assert(pos.x === 1 & pos.y === 2)
  }

}
