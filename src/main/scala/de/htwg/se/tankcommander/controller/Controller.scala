package de.htwg.se.tankcommander.controller

import de.htwg.se.tankcommander.model.{GameField, Obstacle, Player, TankModel}
import de.htwg.se.tankcommander.util.{Observable, UndoManager}
import scala.collection.mutable.ListBuffer

//noinspection ScalaStyle
class Controller(var matchfield: GameField) extends Observable {
  private val undoManager = new UndoManager

  /*
   * Methods to get the Game running or end it
   */
  def createNewGameField(): Unit = {
    matchfield = new GameField
  }

  def setUpGame(): Unit = {
    print("Welcome to Tank-Commander\n")
    print("Player 1 please choose your Name" + "\n")
    val player1 = Player(scala.io.StdIn.readLine())
    print("Player 2 please choose your Name" + "\n")
    val player2 = Player(scala.io.StdIn.readLine())
    val tank1 = new TankModel()
    val tank2 = new TankModel()
    fillGameFieldWithTank((0, 5), tank1)
    fillGameFieldWithTank((10, 5), tank2)
    GameStatus.activePlayer = Option(player1)
    GameStatus.passivePlayer = Option(player2)
    GameStatus.activeTank = Option(tank1)
    GameStatus.passiveTank = Option(tank2)
    notifyObservers()
  }

  def fillGameFieldWithTank(pos: (Int, Int), tank: TankModel): Unit = {
    tank.posC = Option(matchfield.marray(pos._1)(pos._2))
    matchfield.marray(pos._1)(pos._2).containsThisTank = Option(tank)
  }

  def endGame(): Unit = {
    print(GameStatus.activePlayer.get + " Won\n")
  }

  /*
  * Methods to do stuff
  */
  def turnTank(facing: String): Unit = {
    facing match {
      case "up" | "down" | "left" | "right" => GameStatus.activeTank.get.facing = facing
        print("tank turned " + facing)
        lineOfSightContainsTank(matchfield)
        notifyObservers()
      case _ => print("not a viable command\n")
    }
  }


  def endTurn(): Unit = {
    GameStatus.changeActivePlayer()
    lineOfSightContainsTank(matchfield)
    notifyObservers()
  }

  def increaseTurnsGamestatemax(): Unit = {
    GameStatus.currentPlayerActions -= 1
    if (GameStatus.currentPlayerActions == 0) {
      GameStatus.movesLeft = false
    }
  }


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


  def getCurrentHitChanceOfGameStatus: Int = {
    GameStatus.currentHitChance
  }


  def moveTank(input: String): Unit = {
    val activeTank = GameStatus.activeTank.get
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

  def aMoveOfTank(pos: (Int, Int), activeTank: TankModel, x: Boolean): Unit = {
    if (x) {
      matchfield.marray(activeTank.posC.get.x)(activeTank.posC.get.y).containsThisTank = None
      activeTank.posC = Option(matchfield.marray(pos._1)(pos._2))
      matchfield.marray(pos._1)(pos._2).containsThisTank = Option(activeTank)
      lineOfSightContainsTank(matchfield)
      increaseTurnsGamestatemax()
      notifyObservers()
    }
    else {
      print("Move not possible\n")
    }
  }

  def lineOfSightContainsTank2(): Unit = {
    val aXY = (GameStatus.activeTank.get.posC.get.x, GameStatus.activeTank.get.posC.get.y)
    val pXY = (GameStatus.passiveTank.get.posC.get.x, GameStatus.passiveTank.get.posC.get.y)
    val cXY: (Int, Int) = (aXY._1 - pXY._1, aXY._2 - pXY._2)
    var obstacleList = new ListBuffer[Obstacle]()
    cXY match {
      case (0, _) =>
        cXY match {
          //Hoch
          case _ if cXY._2.isPosInfinity =>

          //Runter
          case _ if cXY._2.isNegInfinity =>

        }
      case (_, 0) =>
        cXY match {
          //Links
          case _ if cXY._1.isPosInfinity =>

          //Rechts
          case _ if cXY._1.isNegInfinity =>

        }
    }
  }


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

  }

  /*
   * Methods to shoot stuff
   */

  def shoot(): Unit = {
    if (GameStatus.currentHitChance > 0) {
      if (simShot()) {
        endGame()
      }
      increaseTurnsGamestatemax()
    } else {
      print("No Target in sight\n")
    }
  }

  def calcHitChance(distance: Int, List: List[Obstacle]): Int = {
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
      dealDmgTo(dmg)
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

  def matchfieldToString: String = matchfield.toString

  def solve(): Unit = {
    undoManager.doStep(new MoveCommand(this))
    notifyObservers()
  }

  def undo(): Unit = {
    undoManager.undoStep
    notifyObservers()
  }

  def redo(): Unit = {
    undoManager.redoStep
    notifyObservers()
  }


}


