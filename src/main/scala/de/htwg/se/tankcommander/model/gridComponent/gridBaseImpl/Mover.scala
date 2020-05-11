package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface

import scala.collection.immutable.HashSet.HashSet1
import scala.collection.mutable.ListBuffer

class Mover(matchfield: GameFieldInterface) {

  def lineOfSightContainsTank(obsList: List[Obstacle] = Nil, i: Int = 0):
  Unit = {
    val atXY = (GameStatus.activeTank.get.posC._1, GameStatus.activeTank.get.posC._2)
    val ptXY = (GameStatus.passiveTank.get.posC._1, GameStatus.passiveTank.get.posC._2)
    val cXY: (Int, Int) = (atXY._1 - ptXY._1, atXY._2 - ptXY._2)
    cXY match {
      //Über oder unter
      case (0, _) =>
        cXY match {
          //Hoch zählt runter
          case _ if cXY._2 > 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = "up"))
            if (matchfield.mvector(atXY._1)(atXY._2 - 1 - i).cobstacle.isDefined) {
              if (atXY._2 - 1 - i > ptXY._2 - 1) {
                lineOfSightContainsTank(matchfield.mvector(atXY._1)(atXY._2 - 1 - i).cobstacle.get :: obsList, i - 1)
              }
            }
            if (matchfield.mvector(atXY._1)(atXY._2 - 1 - i).containsThisTank.isDefined) {
              val distance = Math.abs(cXY._2)
              GameStatus.currentHitChance = calcHitChance(distance, obsList)
            }

          //Runter zählt hoch
          case _ if cXY._2 < 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = "down"))
            if (matchfield.mvector(atXY._1)(atXY._2 + 1 + i).cobstacle.isDefined) {
              lineOfSightContainsTank(matchfield.mvector(atXY._1)(atXY._2 + 1 + i).cobstacle.get :: obsList, i + 1)
            }
            if (matchfield.mvector(atXY._1)(atXY._2 + 1 + i).containsThisTank.isDefined) {
              val distance = Math.abs(cXY._2)
              GameStatus.currentHitChance = calcHitChance(distance, obsList)
            }

        }
      //Rechts oder links
      case (_, 0) =>
        cXY match {
          //Links zählt runter
          case _ if cXY._1 > 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = "left"))
            for (i <- (atXY._1 - 1) to ptXY._1 by -1) {
              if (matchfield.mvector(atXY._1 - 1 - i)(atXY._2).cobstacle.isDefined) {
                lineOfSightContainsTank(matchfield.mvector(atXY._1 - 1 - i)(atXY._2).cobstacle.get :: obsList, i - 1)
              }
              if (matchfield.mvector(atXY._1 - 1 - i)(atXY._2).containsThisTank.isDefined) {
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obsList)
              }
            }
          //Rechts zählt hoch
          case _ if cXY._1 < 0 =>
            GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = "right"))
            for (i <- (atXY._1 + 1) to ptXY._1) {
              if (matchfield.mvector(atXY._1 + 1 + i)(atXY._2).cobstacle.isDefined) {
                lineOfSightContainsTank(matchfield.mvector(atXY._1 + 1 + i)(atXY._2).cobstacle.get :: obsList, i + 1)
              }
              if (matchfield.mvector(atXY._1 + 1 + i)(atXY._2).containsThisTank.isDefined) {
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obsList)

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

  def moveTank(input: String): GameFieldInterface = {
    val activeTank = GameStatus.activeTank.get
    val gameField: GameFieldInterface = input match {
      case "up" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        val positionOfActiveTank = (activeTank.posC._1, activeTank.posC._2 - 1)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "down" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        val positionOfActiveTank = (activeTank.posC._1, activeTank.posC._2 + 1)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "left" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        val positionOfActiveTank = (activeTank.posC._1 - 1, activeTank.posC._2)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "right" =>
        GameStatus.activeTank = Option(GameStatus.activeTank.get.copy(facing = input))
        val positionOfActiveTank = (activeTank.posC._1 + 1, activeTank.posC._2)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
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

  def aMoveOfTank(pos: (Int, Int), activeTank: TankModel, movePossible: Boolean): GameFieldInterface = {
    if (movePossible) {
      val temp_matchfield_vector = matchfield.mvector.updated(
        activeTank.posC._1,
        matchfield.mvector(activeTank.posC._1).updated(activeTank.posC._2,
          Cell(activeTank.posC, matchfield.mvector(activeTank.posC._1)(activeTank.posC._2).cobstacle)))
      // matchfield.mvector(activeTank.posC._1)(activeTank.posC._2).containsThisTank = None

      val updated_tank = activeTank.copy(posC = pos)
      // activeTank.posC = pos
      val final_matchfield_vector = temp_matchfield_vector.updated(
        activeTank.posC._1,
        temp_matchfield_vector(pos._1).
          updated(pos._2, Cell(activeTank.posC, matchfield.mvector(activeTank.posC._1)(activeTank.posC._2).cobstacle, Option(updated_tank))))
      // matchfield.mvector(pos._1)(pos._2).containsThisTank = Option(activeTank)
      val new_matchfield = matchfield.update(
        final_matchfield_vector)
      GameStatus.activeTank = Option(updated_tank)
      lineOfSightContainsTank()
      GameStatus.increaseTurns()
      new_matchfield

    }
    else {
      print("Move not possible\n")
      matchfield
    }
  } //notifyObservers()
}
