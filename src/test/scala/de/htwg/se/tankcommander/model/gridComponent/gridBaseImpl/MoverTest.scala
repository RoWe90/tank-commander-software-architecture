package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.mutable.ListBuffer

class MoverTest extends FlatSpec with Matchers {
  "calcHitChance method" should "correctly calc hitchance" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    controller.fillGameFieldWithTank((0, 0), tank1, (5, 0), tank2)
    val mover = new Mover(controller.matchfield)
    var obstacleList = new ListBuffer[Obstacle]()
    obstacleList += new Bush
    val List = obstacleList.toList
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    mover.lineOfSightContainsTank()
    GameStatus.currentHitChance = mover.calcHitChance(5, List)
    assert(GameStatus.currentHitChance === 65)
    mover.moveTank("down")
    assert(controller.matchfield.mvector(0)(1).containsThisTank.get === tank1)
    mover.moveTank("up")
    assert(controller.matchfield.mvector(0)(0).containsThisTank.get === tank1)
    mover.moveTank("right")
    assert(controller.matchfield.mvector(1)(0).containsThisTank.get === tank1)
    mover.moveTank("left")
    assert(controller.matchfield.mvector(0)(0).containsThisTank.get === tank1)
    assert(mover.movePossible((0, -1)) === false)
    assert(mover.movePossible((-1, 0)) === false)
    assert(mover.movePossible((0, 0)) === false)
    assert(mover.movePossible((12, 0)) === false)
    assert(mover.movePossible((0, 12)) === false)
    assert(mover.movePossible((4, 0)) === false)
    assert(mover.movePossible((0, 1)) === true)
    mover.aMoveOfTank((0, 2), tank1, true)
    assert(controller.matchfield.mvector(0)(2).containsThisTank.get === tank1)
    val atXY: (Int, Int) = (GameStatus.activeTank.get.posC._1, GameStatus.activeTank.get.posC._2)
    assert(atXY === (0, 2))
    GameStatus.resetGameStatus()
  }
}
