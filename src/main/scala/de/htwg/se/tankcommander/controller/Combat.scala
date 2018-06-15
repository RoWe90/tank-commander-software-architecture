package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{GameField, Obstacle, TankModel}
import scala.collection.mutable.ListBuffer

//noinspection ScalaStyle
object Combat {
  def lineOfSightContainsTank(matchfield: GameField): Unit = {
    val mXY: (Int, Int) = (matchfield.gridsX, matchfield.gridsy)
    val tXY: (Int, Int) = (GameStatus.activeTank.get.posC.get.x, GameStatus.activeTank.get.posC.get.y)
    GameStatus.activeTank.get.facing match {
      case "up" =>
        var obstacleList = new ListBuffer[Obstacle]()
        for (i <- (tXY._2 - 1) to 0 by -1) {
          if (matchfield.marray(tXY._1)(i).cobstacle.isDefined) {
            obstacleList += matchfield.marray(tXY._1)(i).cobstacle.get
          }
          //Tank in Passable Obstacle not considered
          if (matchfield.marray(tXY._1)(i).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.currentHitChance = calcHitChance(GameStatus.activeTank.get,
              matchfield.marray(tXY._1)(i).containsThisTank.get, i - tXY._2, obstacleCalcList)
          }
        }

      case "left" =>
        var obstacleList = new ListBuffer[Obstacle]()
        for (i <- (tXY._1 - 1) to 0 by -1) {
          if (matchfield.marray(i)(tXY._2).cobstacle.isDefined) {
            obstacleList += matchfield.marray(i)(tXY._2).cobstacle.get
          }
          if (matchfield.marray(i)(tXY._2).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.currentHitChance = calcHitChance(GameStatus.activeTank.get,
              matchfield.marray(i)(tXY._2).containsThisTank.get, tXY._1 - i, obstacleCalcList)
          }
        }

      case "down" =>
        var obstacleList = new ListBuffer[Obstacle]()
        for (i <- (tXY._2 + 1) until mXY._2) {
          if (matchfield.marray(tXY._1)(i).cobstacle.isDefined) {
            obstacleList += matchfield.marray(tXY._1)(i).cobstacle.get
          }
          if (matchfield.marray(tXY._1)(i).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.currentHitChance = calcHitChance(GameStatus.activeTank.get,
              matchfield.marray(tXY._1)(i).containsThisTank.get, tXY._2 - i, obstacleCalcList)
          }
        }

      case "right" =>
        var obstacleList = new ListBuffer[Obstacle]()
        for (i <- (tXY._2 + 1) until mXY._1) {
          if (matchfield.marray(i)(tXY._2).cobstacle.isDefined) {
            obstacleList += matchfield.marray(i)(tXY._2).cobstacle.get
          }
          if (matchfield.marray(i)(tXY._2).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.currentHitChance = calcHitChance(GameStatus.activeTank.get,
              matchfield.marray(i)(tXY._2).containsThisTank.get, i - tXY._1, obstacleCalcList)
          }
        }
    }

  }

  def calcHitChance(gunner: TankModel, target: TankModel, distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitmalus)
    val hitchance = GameStatus.activeTank.get.accuracy - (distance * 10) - obstacleMalus
    if (hitchance > 0) {
      hitchance
    } else {
      0
    }
  }

  def simShot(): Boolean = {
    val r = new scala.util.Random
    val r1 = r.nextInt(100)
    if (GameStatus.currentHitChance >= r1) {
      val dmg = GameStatus.activeTank.get.tankBaseDamage + 40
      dealDmgTo( dmg)
      print("You did: " + dmg + " dmg\n")
      if (GameStatus.passiveTank.get.healthpoints <= 0) {
        return true
      }
      false
    } else {
      print("sadly you missed\n")
      false
    }
  }

  def dealDmgTo(dmg: Int): Unit = {
    GameStatus.passiveTank.get.healthpoints -= dmg
  }
}


