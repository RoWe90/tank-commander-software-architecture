package tankcommander.model

import de.htwg.se.tankcommander.model.Player
import org.scalatest.{FlatSpec, Matchers}

class PlayerTest extends  FlatSpec with Matchers{

  "A Player" should "have a name" in {
    val player = new Player("Test")
    assert(player.name === "Test")
  }
  it should "print its' name" in {
    val player = new Player("Test")
    assert(System.out.println(player.name) === "Test")
  }

}
