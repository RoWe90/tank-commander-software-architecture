package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.{FlatSpec, FunSuite, Matchers}

class MoverTest extends FlatSpec with Matchers {
  "calcHitChance" should "correctly calc hitchance"
  val matchfield = new GameField
  val controller = new Controller(matchfield)
  val tank1 = new TankModel(  )
  val tank2 = new TankModel()
  controller.fillGameFieldWithTank((0, 0), tank1, (5, 0), tank2)
  val mover = new Mover(controller.matchfield)
  val List = List(new Bush)
  mover.lineOfSightContainsTank()
  assert(GameStatus.currentHitChance === 0)
}
