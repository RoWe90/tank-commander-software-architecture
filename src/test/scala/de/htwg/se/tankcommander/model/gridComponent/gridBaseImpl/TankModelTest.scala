package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import org.scalatest.{FlatSpec, Matchers}

class TankModelTest extends FlatSpec with Matchers {

  "A TankModel" should "be properly initialized" in {
    val testTank = new TankModel

    assert(
      testTank.tankBaseDamage === 10 & testTank.accuracy === 100 & testTank.hp === 100
        & testTank.posC === (0, 0) & testTank.facing === "up"
    )
  }
  "deepClone" should "should clone a TankModel" in {
    val testTank = new TankModel
    val testTank2 = testTank.copy()

    assert(
      testTank.tankBaseDamage === testTank2.tankBaseDamage & testTank.accuracy === testTank2.accuracy
        & testTank.hp === testTank2.hp & testTank.posC === testTank2.posC & testTank.facing === testTank2.facing &
        testTank != testTank2

    )

  }

}
