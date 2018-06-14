package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{Combat, GameField, Player, TankModel}
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
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    notifyObservers()
  }

  //noinspection ScalaStyle
  def setPositionTank(pos: (Int, Int), tank: TankModel): Unit = {
    tank.posC = Option(matchfield.marray(pos._1)(pos._2))
    matchfield.marray(pos._1)(pos._2).containsThisTank = Option(tank)
  }

  def endTurn(): Unit = {
    GameStatus.changeActivePlayer()
    notifyObservers()
  }

  def turnTank(facing: String): Unit = {
    facing match {
      case "up" | "down" | "left" | "right" => GameStatus.activeTank.get.facing = facing
        print("tank turned " + facing)
        Combat.lineOfSightContainsTank(GameStatus.activeTank.get, matchfield)
        notifyObservers()
      case _ => print("not a viable command")
    }
  }

  //def shoot(): Unit = {
  //  if (GameStatus.canHit) {
  //    Combat.simShot(GameStatus.activeTank.get, GameStatus.passiveTank.get)
  //    increaseTurnsGamestatemax()
  //  } else {
  //    print("No Target in sight")
  //  }
  //}

  def increaseTurnsGamestatemax(): Unit = {
    GameStatus.currentPlayerActions -= 1
    if (GameStatus.currentPlayerActions == 0) {
      GameStatus.movesLeft = false
    }
  }

  //noinspection ScalaStyle
  def endTurnChangeActivePlayer(): Unit = {
    print("Runde beendet Spielerwechsel")
    GameStatus.changeActivePlayer()
    notifyObservers()
  }

  def checkIfPlayerHasMovesLeft(): Boolean = {
    if (GameStatus.movesLeft) {
      true
    } else {
      print("no turns left")
      false
    }
  }

  def moveTank(input: String): Unit = {
    var activeTank = GameStatus.activeTank.get
    var temp: (Int, Int) = (activeTank.posC.get.x, activeTank.posC.get.y)
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
      matchfield.marray(activeTank.posC.get.x)(activeTank.posC.get.y).containsThisTank = None
      activeTank.posC = Option(matchfield.marray(pos._1)(pos._2))
      matchfield.marray(pos._1)(pos._2).containsThisTank = Option(activeTank)
      Combat.lineOfSightContainsTank(GameStatus.activeTank.get, matchfield)
      increaseTurnsGamestatemax()
      notifyObservers()
    }
    else {
      print("Move not possible")
    }
  }

  def movePossible(pos: (Int, Int)): Boolean = {
    if (pos._1 > matchfield.gridsX - 1) {
      return false
    }
    if (pos._2 > matchfield.gridsy - 1) {
      return false
    }
    if (pos._1 < 0) {
      return false
    }
    if (pos._2 < 0) {
      return false
    }
    if (matchfield.marray(pos._1)(pos._2) != null) {
      if (matchfield.marray(pos._1)(pos._2).containsThisTank.isDefined) {
        return false
      }
      if (matchfield.marray(pos._1)(pos._2).cobstacle.isEmpty) {
        return true
      }
      if (matchfield.marray(pos._1)(pos._2).cobstacle.get.passable) {
        return true
      }
    }
    false
  }

  def matchfieldToString: String = matchfield.toString
}


