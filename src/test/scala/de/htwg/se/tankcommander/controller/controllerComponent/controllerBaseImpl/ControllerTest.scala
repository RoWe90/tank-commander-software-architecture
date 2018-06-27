package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl


import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{Bush, Cell, GameField, TankModel}
import de.htwg.se.tankcommander.model.playerComponent.Player
import de.htwg.se.tankcommander.util.UndoManager
import org.scalatest.{FlatSpec, Matchers}


class ControllerTest extends FlatSpec with Matchers {

  "A Controller" should "set up a Game when asked with properly initialised Objects" in {

  }
  it should "position tanks in the right spot at beginning of match" in {
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    val matchfield = new GameField
    val controller = new Controller(matchfield)
    controller.fillGameFieldWithTank((0, 0), tank1, (5, 5), tank2)
    assert(matchfield.marray(0)(0).containsThisTank === Some(tank1))
    assert(matchfield.marray(5)(5).containsThisTank canEqual Some(tank2))
  }
  it should "change the active player" in {
    val matchfield = new GameField
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    assert(GameStatus.activePlayer.get === player1)
    controller.endTurnChangeActivePlayer()
    assert(GameStatus.activePlayer.get === player2)
  }
  it should "return GameStatus" in {
    val matchfield = new GameField
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
    assert( backup.activePlayer === GameStatus.activePlayer &
      backup.passivePlayer === GameStatus.passivePlayer)
  }
  it should "check if Player has moves left" in {
    GameStatus.movesLeft = true
    val matchfield = new GameField
    val controller = new Controller(matchfield)
    assert(controller.checkIfPlayerHasMovesLeft() === true)
    GameStatus.movesLeft = false
    assert(controller.checkIfPlayerHasMovesLeft() === false)
  }
  it should "print the Gamefield" in {
    val gameField = new GameField
    for (y <- 0 until gameField.gridsX) {
      for (x <- 0 until gameField.gridsY) {
        gameField.marray(x)(y).cobstacle = Option(new Bush)
      }
    }
    val tank1 = new TankModel
    val tank2 = new TankModel
    gameField.marray(0)(0).containsThisTank = Option(tank1)
    gameField.marray(10)(10) = new Cell(10, 10)
    gameField.marray(10)(10).containsThisTank = Option(tank2)
    gameField.marray(9)(10) = new Cell(9, 10)
    val controller = new Controller(gameField)
    assert(controller.toString canEqual  "\n" +
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
    val matchfield = new GameField
    val controller = new Controller(matchfield)
    val player1 = Player("p1")
    val player2 = Player("p2")
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    matchfield.marray(0)(0).containsThisTank = Option(tank1)
    matchfield.marray(10)(10) = new Cell(10, 10)
    matchfield.marray(10)(10).containsThisTank = Option(tank2)
    matchfield.marray(9)(10) = new Cell(9, 10)
    val undoManager = new UndoManager
    controller.move("down")
    assert(matchfield.marray(0)(1).containsThisTank === Some(tank1))
    undoManager.undoStep
    assert(matchfield.marray(0)(0).containsThisTank === Some(tank1))
    undoManager.redoStep
    assert(matchfield.marray(0)(1).containsThisTank === Some(tank1))


  }

}
