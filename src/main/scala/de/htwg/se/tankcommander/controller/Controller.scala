package de.htwg.se.tankcommander.controller


import de.htwg.se.tankcommander.model.{Actions, GameField, Player, TankModel}
import de.htwg.se.tankcommander.util.Observable

class Controller(var matchfield: GameField) extends Observable {

  def createNewMap(): Unit = {
    matchfield = new GameField
  }

  def setUpGame(): Unit = {
    print("Welcome to Tank-Commander")
    print("Player 1 please choose your Name")
    val player1 = Player(scala.io.StdIn.readLine())
    print("Player 2 please choose your Name")
    val player2 = Player(scala.io.StdIn.readLine())
    print("Player 1 please name your Tank")
    var tank1 = new TankModel(scala.io.StdIn.readLine())
    print("Player 1 please name your Tank")
    val tank2 = new TankModel(scala.io.StdIn.readLine())

    setPositionTank((3, 3), tank1)
    setPositionTank((8, 8), tank1)

    GameStatus.gameStarted = true
    GameStatus.players.update(0, player1)
    GameStatus.players.update(1, player2)
    notifyObservers
  }

  //noinspection ScalaStyle
  def setPositionTank(pos: (Int, Int), tank: TankModel): Unit = {
    //tank.position && tank.position??
    if (tank.position != null && tank.position != null) {
      matchfield.matchfieldarray(tank.position.x)(tank.position.y).containsThisTank = null
      tank.position = null
      tank.position = matchfield.matchfieldarray(pos._1)(pos._2)
      matchfield.matchfieldarray(pos._1)(pos._2).containsThisTank = tank
    } else {
      matchfield.matchfieldarray(pos._1)(pos._2).containsThisTank = tank
      tank.position = matchfield.matchfieldarray(pos._1)(pos._2)
    }

  }

  def endTurn(): Unit = {
    GameStatus.changeActivePlayer()
    notifyObservers
  }

  def turnTank(facing: String): Unit = {
    facing match {
      case "up" | "down" | "left" | "right" => GameStatus.activeTank.facing = facing
        Actions.lineOfSightContainsTank(GameStatus.activeTank, matchfield)
      case _ => print("not a viable command")
    }
  }

  def shoot(): Unit = {
    if (GameStatus.canHit) {
      Actions.simShot(GameStatus.activeTank, GameStatus.passiveTank)
      increaseTurnsGamestatemax
    } else {
      print("No Target in sight")
    }
  }

  def increaseTurnsGamestatemax(): Unit = {
    GameStatus.currentPlayerActions -= 1
    checkPlayerchange
  }

  def checkPlayerchange(): Unit = {
    if (!GameStatus.movesLeft) {
      print("No moves left only change direction, End turn?")
    }
  }

  def moveTank(input: String): Unit = {
    var activeTank = GameStatus.activeTank
    var temp = (activeTank.getPositionAsIntX(), activeTank.getPositionAsIntY())
    input match {
      case "up" =>
        temp = (temp._1, temp._2 + 1)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "down" =>
        temp = (temp._1, temp._2 - 1)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "left" =>
        temp = (temp._1 - 1, temp._2)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "right" =>
        temp = (temp._1 + 1, temp._2)
        aMoveOfTank(temp, activeTank, movePossible(temp))
    }
  }

  def aMoveOfTank(pos: (Int, Int), activeTank: TankModel, x: Boolean): Unit = {
    if (x) {
      matchfield.matchfieldarray(activeTank.position.x)(activeTank.position.y).containsThisTank = null
      activeTank.position = matchfield.matchfieldarray(pos._1)(pos._2)
      matchfield.matchfieldarray(pos._1)(pos._2).containsThisTank = activeTank
      Actions.lineOfSightContainsTank(GameStatus.activeTank, matchfield)
      increaseTurnsGamestatemax
      notifyObservers
    }
    else {
      print("Move not possible")
    }
  }

  def movePossible(pos: (Int, Int)): Boolean = if (matchfield.matchfieldarray(pos._1)(pos._2).cellobstacle.passable) true else false

  def matchfieldToString: String = matchfield.toString
}


