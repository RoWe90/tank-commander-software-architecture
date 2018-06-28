package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.playerComponent.Player
import org.scalatest.{FlatSpec, Matchers}
//
class ShooterTest extends FlatSpec with Matchers {
  "Shooter" should "deal Dmg to passive Tank" in {
    GameStatus.currentPlayerActions = 2
    val gamefield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(gamefield)
    val shot = new Shooter
    val player1 = new Player("test")
    val player2 = new Player("test1")
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    val activeTank = new TankModel
    val passiveTank = new TankModel
    GameStatus.activeTank = Option(activeTank)
    GameStatus.passiveTank = Option(passiveTank)
    shot.dealDmgTo(20)
    assert(GameStatus.passiveTank.get.hp === 80)
    shot.shoot()
    assert(GameStatus.currentPlayerActions === 2)
    GameStatus.currentHitChance = 100
    shot.shoot()
    assert(GameStatus.currentPlayerActions === 1)
    GameStatus.resetGameStatus()
  }
}
