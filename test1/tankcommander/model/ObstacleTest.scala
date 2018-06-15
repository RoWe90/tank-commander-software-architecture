package tankcommander.model

import de.htwg.se.tankcommander.model.{Bush, Obstacle}
import org.scalatest.{FlatSpec, Matchers}

class ObstacleTest extends FlatSpec with Matchers {

  "An Obstacle" should "have a name, description, symbol, passable and hitmalus depending on its class" in {
    val obstacle = new Bush()
    assert(obstacle.name === "Gebüsche")
    assert(obstacle.desc === "Gebüsche verringern die Präzision von Schüssen")
    assert(obstacle.passable === true)
    assert(obstacle.shortName === "\u16E4")
    assert(obstacle.hitmalus === 10)
  }

}
