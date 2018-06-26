package model

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{GameField, TankModel}
import de.htwg.se.tankcommander.model.playerComponent.Player
import de.htwg.se.tankcommander.util.UndoManager
import org.scalatest.{FlatSpec, Matchers}

class ShooterTest extends FlatSpec with Matchers{

  "Shooter" should "deal Dmg to passive Tank" in {

    val gameStatus = new GameStatus
    val gamefield = new GameField
    val controller = new Controller(gamefield)
    val player1 = new Player("test")
    val player2 = new Player("test1")
    gameStatus.activePlayer = Option(player1)
    gameStatus.passivePlayer=Option(player2)
    val undo = new UndoManager
    val activeTank = new TankModel
    val passiveTank = new TankModel

    gameStatus.activeTank = Option(activeTank)
    gameStatus.passiveTank = Option(passiveTank)

    println(gameStatus.passiveTank.get.hp)


  }

}
