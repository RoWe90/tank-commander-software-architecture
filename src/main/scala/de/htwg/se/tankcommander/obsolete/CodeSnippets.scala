package de.htwg.se.tankcommander.obsolete

class CodeSnippets {
  /*
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
          if (matchfield.marray(tXY._1)(i).containsThisTank.isDefined) {
            val obstacleCalcList = obstacleList.toList
            GameStatus.currentHitChance = calcHitChance(i - tXY._2, obstacleCalcList)
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
            GameStatus.currentHitChance = calcHitChance(tXY._1 - i, obstacleCalcList)
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
            GameStatus.currentHitChance = calcHitChance(tXY._2 - i, obstacleCalcList)
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
            GameStatus.currentHitChance = calcHitChance(i - tXY._1, obstacleCalcList)
          }
        }
    }

  def getCurrentHitChanceOfGameStatus: Int = {
    GameStatus.currentHitChance
  }

    def turnTank(facing: String): Unit = {
    facing match {
      case "up" | "down" | "left" | "right" => GameStatus.activeTank.get.facing = facing
        print("tank turned " + facing)
        lineOfSightContainsTank()
        notifyObservers()
      case _ => print("not a viable command\n")
    }
  }

  }*/
}
