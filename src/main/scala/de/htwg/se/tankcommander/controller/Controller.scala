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
        lineOfSightContainsTank()
        notifyObservers()
      case _ => print("not a viable command\n")
    }
  }

  def endTurn(): Unit = {
    GameStatus.changeActivePlayer()
    lineOfSightContainsTank()
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
    lineOfSightContainsTank()
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



  def lineOfSightContainsTank(): Unit = {
    val atXY = (GameStatus.activeTank.get.posC.get.x, GameStatus.activeTank.get.posC.get.y)
    val ptXY = (GameStatus.passiveTank.get.posC.get.x, GameStatus.passiveTank.get.posC.get.y)
    val cXY: (Int, Int) = (atXY._1 - ptXY._1, atXY._2 - ptXY._2)
    var obstacleList = new ListBuffer[Obstacle]()
    cXY match {
      //Über oder unter
      case (0, _) =>
        cXY match {
          //Hoch zählt runter
          case _ if cXY._2 > 0 =>
            GameStatus.activeTank.get.facing = "up"
            for (i <- (atXY._2 - 1) to ptXY._2 by -1) {
              if (matchfield.marray(atXY._1)(i).cobstacle.isDefined) {
                obstacleList += matchfield.marray(atXY._1)(i).cobstacle.get
              }
              if (matchfield.marray(atXY._1)(i).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._2)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Runter zählt hoch
          case _ if cXY._2 < 0 =>
            GameStatus.activeTank.get.facing = "down"
            for (i <- (atXY._2 + 1) to ptXY._2) {
              if (matchfield.marray(atXY._1)(i).cobstacle.isDefined) {
                obstacleList += matchfield.marray(atXY._1)(i).cobstacle.get
              }
              if (matchfield.marray(atXY._1)(i).containsThisTank.isDefined) {
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
            GameStatus.activeTank.get.facing = "left"
            for (i <- (atXY._1 - 1) to ptXY._1 by -1) {
              if (matchfield.marray(i)(atXY._2).cobstacle.isDefined) {
                obstacleList += matchfield.marray(i)(atXY._2).cobstacle.get
              }
              if (matchfield.marray(i)(atXY._2).containsThisTank.isDefined) {
                val obstacleCalcList = obstacleList.toList
                val distance = Math.abs(cXY._1)
                GameStatus.currentHitChance = calcHitChance(distance, obstacleCalcList)
              }
            }
          //Rechts zählt hoch
          case _ if cXY._1 < 0 =>
            GameStatus.activeTank.get.facing = "right"
            for (i <- (atXY._1 + 1) to ptXY._1) {
              if (matchfield.marray(i)(atXY._2).cobstacle.isDefined) {
                obstacleList += matchfield.marray(i)(atXY._2).cobstacle.get
              }
              if (matchfield.marray(i)(atXY._2).containsThisTank.isDefined) {
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

  def createGameStatusBackup: GameStatusBackUp ={
    val backup = new GameStatusBackUp
    backup
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

  /*
 * Undo manager
 */

  def move(s:String): Unit = {
    undoManager.doStep(new MoveCommand(this,s))
    notifyObservers()
  }
  def shootC(): Unit = {
    undoManager.doStep(new ShootCommand(this))
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


