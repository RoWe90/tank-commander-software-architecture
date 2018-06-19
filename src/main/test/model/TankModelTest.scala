package model

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.TankModel
import org.scalatest.{FlatSpec, Matchers}

class TankModelTest extends FlatSpec with Matchers {

  "A TankModel" should "be properly initialized" in {
    val testTank = new TankModel

    assert(
      testTank.tankBaseDamage === 10 & testTank.accuracy === 100 & testTank.healthpoints === 100
        & testTank.posC === None & testTank.facing === "up"
    )
  }
  /* it should "have damage dealt correctly" in {
     val passiveTank = new TankModel
     val activeTank = new TankModel
     passiveTank.facing = "down"
     val shooterTest = new Shooter
     var gameStatus = new GameStatusBackUp
     gameStatus.activeTank = Some(activeTank)
     gameStatus.passiveTank = Some(passiveTank)
     shooterTest.dealDmgTo(10)

     assert(passiveTank.healthpoints === 90)
   }*/



}
