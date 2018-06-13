package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.controller.GameStatus

import scala.collection.mutable.ListBuffer


//noinspection ScalaStyle
object Combat {

  def lineOfSightContainsTank(gunner: TankModel, matchfield: GameField): (Boolean, Int, Int, Int) = {
    val sx: Int = matchfield.gridsize_x - 1
    val sy: Int = matchfield.gridsize_y - 1
    val tx: Int = gunner.getPositionAsIntX()
    val ty: Int = gunner.getPositionAsIntY()
    var obstacleList = new ListBuffer[Obstacle]()
    gunner.facing match {
      case "up" =>
        for (i <- (ty - 1) to 0 by -1) {
          if (matchfield.matchfieldarray(tx)(i).cellobstacle != null) {
            obstacleList += matchfield.matchfieldarray(tx)(i).cellobstacle
          }
          //Tank in Passable Obstacle not considered
          if (matchfield.matchfieldarray(tx)(i).containsThisTank != null) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.setCurrentHitrate(true, calcHitChance(gunner, matchfield.matchfieldarray(tx)(i).containsThisTank, i - ty, obstacleCalcList))
          }
        }

      case "down" =>
        for (i <- (ty + 1) to sy) {
          if (matchfield.matchfieldarray(tx)(i).cellobstacle != null) {
            obstacleList += matchfield.matchfieldarray(tx)(i).cellobstacle
          }
          if (matchfield.matchfieldarray(tx)(i).containsThisTank != null) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.setCurrentHitrate(true, calcHitChance(gunner, matchfield.matchfieldarray(tx)(i).containsThisTank, ty - i, obstacleCalcList))
          }
        }
      case "left" =>
        for (i <- (tx - 1) to 0 by -1) {
          if (matchfield.matchfieldarray(i)(ty).cellobstacle != null) {
            obstacleList += matchfield.matchfieldarray(i)(ty).cellobstacle
          }
          if (matchfield.matchfieldarray(i)(ty).containsThisTank != null) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.setCurrentHitrate(true, calcHitChance(gunner, matchfield.matchfieldarray(tx)(i).containsThisTank, tx - i, obstacleCalcList))
          }
        }
      case "right" =>
        for (i <- (ty + 1) to sx) {
          if (matchfield.matchfieldarray(i)(ty).cellobstacle != null) {
            obstacleList += matchfield.matchfieldarray(i)(ty).cellobstacle
          }
          if (matchfield.matchfieldarray(i)(ty).containsThisTank != null) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.setCurrentHitrate(true, calcHitChance(gunner, matchfield.matchfieldarray(tx)(i).containsThisTank, i - tx, obstacleCalcList))
          }
        }
    }
    (false, 0, 0, 0)
  }

  def calcHitChance(gunner: TankModel, target: TankModel, distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitmalus)
    var hitchance = GameStatus.activeTank.accuracy - (distance * 10) - obstacleMalus
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


