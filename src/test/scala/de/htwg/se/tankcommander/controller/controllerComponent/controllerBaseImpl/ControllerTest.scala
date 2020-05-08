package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl


import java.io.ByteArrayInputStream

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl._
import de.htwg.se.tankcommander.model.playerComponent.Player
import org.scalatest.{FlatSpec, Matchers}


class ControllerTest extends FlatSpec with Matchers {

  "A Controller" should "set up a Game when asked with properly initialised Objects" in {

  }
  it should "position tanks in the right spot at beginning of match" in {
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    controller.fillGameFieldWithTank((0, 0), tank1, (5, 5), tank2)
    assert(matchfield.mvector(0)(0).containsThisTank === Some(tank1))
    assert(matchfield.mvector(5)(5).containsThisTank canEqual Some(tank2))
  }
  it should "change the active player" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    assert(GameStatus.activePlayer.get === player1)
    controller.endTurnChangeActivePlayer()
    assert(GameStatus.activePlayer.get === player2)
    GameStatus.resetGameStatus()
  }
  it should "return GameStatus" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    val backup = controller.createGameStatusBackup
    assert(backup.activePlayer === GameStatus.activePlayer &
      backup.passivePlayer === GameStatus.passivePlayer)
    GameStatus.resetGameStatus()
  }
  it should "check if Player has moves left" in {
    GameStatus.movesLeft = true
    val matchfield = GameFieldFactory.apply("Map 1")
    val controller = new Controller(matchfield)
    assert(controller.checkIfPlayerHasMovesLeft() === true)
    GameStatus.movesLeft = false
    assert(controller.checkIfPlayerHasMovesLeft() === false)
    GameStatus.resetGameStatus()
  }
  it should "print the Gamefield" in {

    val gameField = GameFieldFactory.apply("Map 1")
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        gameField.mvector(x)(y).cobstacle = Option(new Bush)
      }
    }
    val tank1 = new TankModel
    val tank2 = new TankModel
    val player1 = new Player("p1")
    val player2 = new Player("p2")
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.movesLeft = true
    GameStatus.currentPlayerActions = 2
    GameStatus.currentHitChance = 0
    gameField.mvector(0)(0).containsThisTank = Option(tank1)
    gameField.mvector(10)(10) = new Cell(10, 10)
    gameField.mvector(10)(10).containsThisTank = Option(tank2)
    gameField.mvector(9)(10) = new Cell(9, 10)
    val controller = new Controller(gameField)
    val gameFieldtoString = controller.toString
    assert(controller.matchfieldToString === "\n" +
      "T  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  B  B  \n" +
      "B  B  B  B  B  B  B  B  B  o  T  HS: 0\n"
    )
  }
  "The UndoManager" should "remember the actions taken accordingly" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    for (y <- 0 until matchfield.gridsX) {
      for (x <- 0 until matchfield.gridsY) {
        matchfield.mvector(x)(y).cobstacle = Option(new Bush)
      }
    }
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    matchfield.mvector(0)(0).containsThisTank = Option(tank1)
    matchfield.mvector(10)(10).containsThisTank = Option(tank2)
    assert(matchfield.mvector(0)(0).containsThisTank === Some(tank1))
    controller.move("down")
    assert(matchfield.mvector(0)(1).containsThisTank === Some(tank1))
    controller.undo()
    assert(matchfield.mvector(0)(1).containsThisTank === Some(tank1))
    controller.redo()
    assert(matchfield.mvector(0)(1).containsThisTank === Some(tank1))

    GameStatus.resetGameStatus()


  }
  it should "save and load correctly" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    for (y <- 0 until matchfield.gridsX) {
      for (x <- 0 until matchfield.gridsY) {
        matchfield.mvector(x)(y).cobstacle = Option(new Bush)
      }
    }
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    matchfield.mvector(0)(0).containsThisTank = Option(tank1)
    matchfield.mvector(10)(10).containsThisTank = Option(tank2)
    assert(matchfield.mvector(0)(0).containsThisTank === Some(tank1))
    controller.move("down")
    assert(matchfield.mvector(0)(1).containsThisTank === Some(tank1))
    controller.save()
    assert(matchfield.mvector(0)(1).containsThisTank === Some(tank1))
    controller.move("down")
    assert(matchfield.mvector(0)(2).containsThisTank === Some(tank1))
    controller.load()
    assert(matchfield.mvector(0)(2).containsThisTank === Some(tank1))
    GameStatus.resetGameStatus()
  }
  "Shoot method" should "shoot" in {
    val matchfield = GameFieldFactory.apply("Map 1")
    for (y <- 0 until matchfield.gridsX) {
      for (x <- 0 until matchfield.gridsY) {
        matchfield.mvector(x)(y).cobstacle = Option(new Bush)
      }
    }
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    GameStatus.movesLeft = true
    GameStatus.currentPlayerActions = 2
    GameStatus.currentHitChance = 0
    matchfield.mvector(5)(5).containsThisTank = Option(tank1)
    matchfield.mvector(6)(5).containsThisTank = Option(tank2)
    controller.shoot()
    assert(GameStatus.currentPlayerActions === 2)
    GameStatus.resetGameStatus()

  }
  "SetupGame" should "set up a game correctly when parameters are given" in {
    val controller = new Controller
    controller.setUpGame("p1", "p2", map = "Map 1")
    assert(GameStatus.activePlayer.get.name === "p1")
    assert(GameStatus.passivePlayer.get.name === "p2")
    assert(controller.mapChosen === "Map 1")
  GameStatus.resetGameStatus()
  }
  it should "set up a game correctly without parameters" in {
    val controller = new Controller
    val in = new ByteArrayInputStream(("p1\np2\nMap 1\n").getBytes)
    System.setIn(in)
    Console.withIn(in){
      controller.setUpGame()
    assert(GameStatus.activePlayer.get.name === "p1")
    assert(GameStatus.passivePlayer.get.name === "p2")
    assert(controller.mapChosen === "Map 1")}
    GameStatus.resetGameStatus()
  }
}
