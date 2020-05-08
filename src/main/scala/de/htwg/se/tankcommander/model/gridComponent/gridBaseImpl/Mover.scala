package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface

import scala.collection.mutable.ListBuffer

class Mover(matchfield: GameFieldInterface) {

  def lineOfSightContainsTank(): Unit = {
    val atXY = (GameStatus.activeTank.get.posC._1, GameStatus.activeTank.get.posC._2)
    val ptXY = (GameStatus.passiveTank.get.posC._1, GameStatus.passiveTank.get.posC._2)
    val cXY: (Int, Int) = (atXY._1 - ptXY._1, atXY._2 - ptXY._2)
    var obstacleList = new ListBuffer[Obstacle]()
    cXY match {
      //Über oder unter
      case (0, _) =>
        cXY match {
          //Hoch zählt runter
          case _ if cXY._2 > 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing="up"))
            for (i <- (atXY._2 - 1) to ptXY._2 by -1) {
              if (matchfield.mvector(atXY._1)(i).cobstacle.isDefined) {
                obstacleList += matchfield.mvector(atXY._1)(i).cobstacle.get
              }
              if (matchfield.mvector(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Runter zählt hoch
          case _ if cXY._2 < 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing="down"))
            for (i <- (atXY._2 + 1) to ptXY._2) {
              if (matchfield.mvector(atXY._1)(i).cobstacle.isDefined) {
                obstacleList += matchfield.mvector(atXY._1)(i).cobstacle.get
              }
              if (matchfield.mvector(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
        }
      //Rechts oder links
      case (_, 0) =>
        cXY match {
          //Links zählt runter
          case _ if cXY._1 > 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing="left"))
            for (i <- (atXY._1 - 1) to ptXY._1 by -1) {
              if (matchfield.mvector(i)(atXY._2).cobstacle.isDefined) {
                obstacleList += matchfield.mvector(i)(atXY._2).cobstacle.get
              }
              if (matchfield.mvector(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Rechts zählt hoch
          case _ if cXY._1 < 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing="right"))
            for (i <- (atXY._1 + 1) to ptXY._1) {
              if (matchfield.mvector(i)(atXY._2).cobstacle.isDefined) {
                obstacleList += matchfield.mvector(i)(atXY._2).cobstacle.get
              }
              if (matchfield.mvector(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)

              }
            }
        }
      case _ => GameStatus.currentHitChance = 0
    }
  }
  def calcHitChance(distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitmalus)
    val hitchance = GameStatus.activeTank.get.accuracy - (distance * 5) - obstacleMalus
    if (hitchance > 0) {
      hitchance
    } else {
      0
    }
  }
//TODO hier weitermachen
  def moveTank(input: String): GameFieldInterface = {
    val activeTank = GameStatus.activeTank.get
    var positionOfActiveTank: (Int, Int) = (activeTank.posC._1, activeTank.posC._2)
    var gameField: GameFieldInterface = matchfield
    input match {
      case "up" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        positionOfActiveTank = (positionOfActiveTank._1, positionOfActiveTank._2 - 1)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "down" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        positionOfActiveTank = (positionOfActiveTank._1, positionOfActiveTank._2 + 1)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "left" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        positionOfActiveTank = (positionOfActiveTank._1 - 1, positionOfActiveTank._2)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "right" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        positionOfActiveTank = (positionOfActiveTank._1 + 1, positionOfActiveTank._2)
        gameField = aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
    }

    gameField
  }

  def movePossible(pos: (Int, Int)): Boolean = {
    if (pos._1 > matchfield.gridsX - 1) {
      return false
    }
    if (pos._2 > matchfield.gridsY - 1) {
      return false
    }
    if (pos._1 < 0) {
      return false
    }
    if (pos._2 < 0) {
      return false
    }
    if (matchfield.mvector(pos._1)(pos._2) != null) {
      if (matchfield.mvector(pos._1)(pos._2).containsThisTank.isDefined) {
        return false
      }
      if (matchfield.mvector(pos._1)(pos._2).cobstacle.isEmpty) {
        return true
      }
      if (matchfield.mvector(pos._1)(pos._2).cobstacle.get.passable) {
        return true
      }
    }
    false
  }

  def aMoveOfTank(pos: (Int, Int), activeTank: TankModel, x: Boolean): GameFieldInterface = {
    if (x) {
      val temp_matchfield = matchfield.mvector.updated(activeTank.posC._1,matchfield.mvector(activeTank.posC._1).updated(activeTank.posC._2,None))
      //matchfield.mvector(activeTank.posC._1)(activeTank.posC._2).containsThisTank = None

      val new_tank = activeTank.copy(posC = pos)
      val final_matchfield = temp_matchfield.updated(activeTank.posC._1,temp_matchfield(activeTank.posC._1).updated(activeTank.posC._2,Option(activeTank)))

      //matchfield.mvector(pos._1)(pos._2).containsThisTank = Option(activeTank)
      lineOfSightContainsTank()
      GameStatus.increaseTurns()
      matchfield

    }
    else {
      print("Move not possible\n")
      matchfield
    }
  } //notifyObservers()
}
