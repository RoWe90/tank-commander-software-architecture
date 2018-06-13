package de.htwg.se.tankcommander.controller


import de.htwg.se.tankcommander.model.{Actions, GameField, Player, TankModel}
import de.htwg.se.tankcommander.util.Observable

class Controller(var matchfield: GameField) extends Observable {

  def createNewMap(): Unit = {
    matchfield = new GameField
  }

  def setUpGame(): Unit = {
    print("Welcome to Tank-Commander" + "\n")
    print("Player 1 please choose your Name" + "\n")
    val player1 = Player(scala.io.StdIn.readLine())
    print("Player 2 please choose your Name" + "\n")
    val player2 = Player(scala.io.StdIn.readLine())
    var tank1 = new TankModel()
    var tank2 = new TankModel()

    setPositionTank((0, 5), tank1)
    setPositionTank((10, 5), tank2)

    GameStatus.gameStarted = true
    GameStatus.players.update(0, player1)
    GameStatus.players.update(1, player2)
    GameStatus.activeTank = tank1
    GameStatus.passiveTank = tank2
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

  //noinspection ScalaStyle
  def checkPlayerchange(): Unit = {
    if (GameStatus.movesLeft == false) {
      print("No moves left only change direction, End turn?")
    }
  }

  def moveTank(input: String): Unit = {
    var activeTank = GameStatus.activeTank
    var temp = (activeTank.getPositionAsIntX(), activeTank.getPositionAsIntY())
    input match {
      case "up" =>
        temp = (temp._1, temp._2 - 1)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "down" =>
        temp = (temp._1, temp._2 + 1)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "left" =>
        temp = (temp._1 - 1, temp._2)
        aMoveOfTank(temp, activeTank, movePossible(temp))
      case "right" =>
        temp = (temp._1 + 1, temp._2)
        aMoveOfTank(temp, activeTank, movePossible(temp))
    }
  }

  def getCurrentHitChanceOfGamestatus: Int = {
    GameStatus.currentHitChance
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

  def movePossible(pos: (Int, Int)): Boolean = {
    if (pos._1 > matchfield.gridsize_x - 1) {
      return false
    }
    if (pos._2 > matchfield.gridsize_y - 1) {
      return false
    }
    if (pos._1 < 0) {
      return false
    }
    if (pos._2 < 0) {
      return false
    }

    if (matchfield.matchfieldarray(pos._1)(pos._2) != null) {
      if (matchfield.matchfieldarray(pos._1)(pos._2).cellobstacle == null) {
        return true
      }
      if (matchfield.matchfieldarray(pos._1)(pos._2).cellobstacle.passable) {
        return true
      }
    }
    false
  }

  def matchfieldToString: String = matchfield.toString
}


