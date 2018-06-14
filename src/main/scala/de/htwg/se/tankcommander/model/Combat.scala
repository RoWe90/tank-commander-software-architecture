package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.controller.GameStatus
import scala.collection.mutable.ListBuffer

//noinspection ScalaStyle
object Combat {
  def lineOfSightContainsTank(gunner: TankModel, matchfield: GameField): (Boolean, Int, Int, Int) = {
    val mXY: (Int, Int) = (matchfield.gridsX, matchfield.gridsy)
    val tXY: (Int, Int) = (gunner.posC.get.x, gunner.posC.get.y)
    gunner.facing match {
      case "up" =>
        var obstacleList = new ListBuffer[Obstacle]()
        for (i <- (tXY._2 - 1) to 0 by -1) {
          if (matchfield.marray(tXY._1)(i).cobstacle.isDefined) {
            obstacleList += matchfield.marray(tXY._1)(i).cobstacle.get
          }
          //Tank in Passable Obstacle not considered
          if (matchfield.marray(tXY._1)(i).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.setCurrentHitrate(bool = true, calcHitChance(gunner,
              matchfield.marray(tXY._1)(i).containsThisTank.get, i - tXY._2, obstacleCalcList))
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
            GameStatus.setCurrentHitrate(bool = true, calcHitChance(gunner,
              matchfield.marray(i)(tXY._2).containsThisTank.get, tXY._1 - i, obstacleCalcList))
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
            GameStatus.setCurrentHitrate(bool = true, calcHitChance(gunner,
              matchfield.marray(tXY._1)(i).containsThisTank.get, tXY._2 - i, obstacleCalcList))
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
            GameStatus.setCurrentHitrate(bool = true, calcHitChance(gunner,
              matchfield.marray(i)(tXY._2).containsThisTank.get, i - tXY._1, obstacleCalcList))
          }
        }
    }
    (false, 0, 0, 0)
  }

  def calcHitChance(gunner: TankModel, target: TankModel, distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitmalus)
    var hitchance = GameStatus.activeTank.get.accuracy - (distance * 10) - obstacleMalus
    if (hitchance > 0) {
      hitchance
    } else {
      0
    }
  }

  def simShot(gunner: TankModel, target: TankModel): Unit = {
    val r = new scala.util.Random
    val r1 = r.nextInt(100)
    if (GameStatus.currentHitChance >= r1) {
      var dmg = gunner.tankBaseDamage
      takeDmg(target, dmg)
      print("You did: " + dmg + " dmg")
    } else {
      print("sadly you missed")
    }
  }

  def takeDmg(target: TankModel, dmg: Int): Unit = {
    target.healthpoints -= dmg
  }
}


