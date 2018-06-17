package de.htwg.se.tankcommander.model

import org.scalatest.{FlatSpec, Matchers}

class PlayerTest extends FlatSpec with Matchers {

  "A Player" should "have a Name" in {
    val testPlayer = Player("aPlayer")
    assert(testPlayer.name === "aPlayer")
  }

}
