package tankcommander.model

import tankcommander.gameState.GameStatus
import tankcommander.model.girdComponent.GameFieldInterface
import tankcommander.model.girdComponent.gridBaseImpl.{Cell, Obstacle}
import tankcommander.util.AttributeHandler

class Mover(matchfield: GameFieldInterface) {

  val attributeHandler = AttributeHandler()

  def moveTank(input: String): GameFieldInterface = {
    val activeTank = GameStatus.activeTank.get
    val gameField: GameFieldInterface = input match {
      case "up" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", input)
            Some(i)
          }
          case None => None
        }
        val positionOfActiveTank = (attributeHandler.getPosC(activeTank)._1, attributeHandler.getPosC(activeTank)._2 - 1)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "down" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", input)
            Some(i)
          }
          case None => None
        }
        val positionOfActiveTank = (attributeHandler.getPosC(activeTank)._1, attributeHandler.getPosC(activeTank)._2 + 1)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "left" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", input)
            Some(i)
          }
          case None => None
        }
        val positionOfActiveTank = (attributeHandler.getPosC(activeTank)._1 - 1, attributeHandler.getPosC(activeTank)._2)
        aMoveOfTank(positionOfActiveTank, activeTank, movePossible(positionOfActiveTank))
      case "right" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", input)
            Some(i)
          }
          case None => None
        }
        val positionOfActiveTank = (attributeHandler.getPosC(activeTank)._1 + 1, attributeHandler.getPosC(activeTank)._2)
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

  def aMoveOfTank(pos: (Int, Int), activeTank: Int, movePossible: Boolean): GameFieldInterface = {
    if (movePossible) {
      val temp_matchfield_vector = matchfield.mvector.updated(
        attributeHandler.getPosC(activeTank)._1,
        matchfield.mvector(attributeHandler.getPosC(activeTank)._1).updated(attributeHandler.getPosC(activeTank)._2,
          Cell(attributeHandler.getPosC(activeTank), matchfield.mvector(attributeHandler.getPosC(activeTank)._1)(attributeHandler.getPosC(activeTank)._2).cobstacle)))
      // matchfield.mvector(activeTank.posC._1)(activeTank.posC._2).containsThisTank = None

      val activeTankPosC = attributeHandler.getPosC(activeTank)
      attributeHandler.setAttribute(activeTank, "posC", pos._1 + "_" + pos._2)
      // activeTank.posC = pos
      val final_matchfield_vector = temp_matchfield_vector.updated(
        activeTankPosC._1,
        temp_matchfield_vector(pos._1).
          updated(pos._2, Cell(activeTankPosC, matchfield.mvector(activeTankPosC._1)(activeTankPosC._2).cobstacle, Some(activeTank))))
      // matchfield.mvector(pos._1)(pos._2).containsThisTank = Option(activeTank)
      val new_matchfield = matchfield.update(
        final_matchfield_vector)
      GameStatus.activeTank = Some(activeTank)
      lineOfSightContainsTank()
      GameStatus.increaseTurns()
      new_matchfield

    }
    else {
      print("Move not possible\n")
      matchfield
    }
  } //notifyObservers()

  def lineOfSightContainsTank(obsList: List[Obstacle] = Nil, i: Int = 0): Unit = {
    val activeTankPos = GameStatus.activeTank match {
      case Some(i) => {
        attributeHandler.getPosC(i)
      }
      case None => (0, 0)
    }
    val passiveTankPos = GameStatus.passiveTank match {
      case Some(i) => attributeHandler.getPosC(i)
      case None => (0, 0)
    }
    val tankIntersection: (Int, Int) = (activeTankPos._1 - passiveTankPos._1, activeTankPos._2 - passiveTankPos._2)
    // Ist der feindliche Tank auf einer Linie mit uns?
    tankIntersection match {
      // Feindlicher Tank ist über uns
      case (0, _) if tankIntersection._2 > 0 =>
        val distance = Math.abs(tankIntersection._2)
        GameStatus.currentHitChance = calcHitChance(distance, accumulateObstacles(activeTankPos, passiveTankPos, "up"))
      //Feindlicher Tank ist unter uns
      case (0, _) if tankIntersection._2 < 0 =>
        val distance = Math.abs(tankIntersection._2)
        GameStatus.currentHitChance = calcHitChance(distance, accumulateObstacles(activeTankPos, passiveTankPos, "up"))

      //Feindlicher Tank ist links von uns uns
      case (_, 0) if tankIntersection._1 > 0 =>
        val distance = Math.abs(tankIntersection._1)
        GameStatus.currentHitChance = calcHitChance(distance, accumulateObstacles(activeTankPos, passiveTankPos, "up"))
      //Feindlicher Tank ist rechts von uns
      case (_, 0) if tankIntersection._1 < 0 =>
        val distance = Math.abs(tankIntersection._1)
        GameStatus.currentHitChance = calcHitChance(distance, accumulateObstacles(activeTankPos, passiveTankPos, "up"))
      //Tank ist nicht auf einer Linie
      case _ => GameStatus.currentHitChance = 0
    }
  }

  def accumulateObstacles(posActiveTank: (Int, Int), posPassiveTank: (Int, Int), facing: String): List[Obstacle] = {
    var obsList = List[Obstacle]()
    obsList = facing match {
      case "up" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", "up")
            Some(i)
          }
          case None => None
        }
        for (i <- (posActiveTank._2 - 1) to posPassiveTank._2 by -1) {
          if (matchfield.mvector(posActiveTank._1)(posActiveTank._2 - 1 - i).cobstacle.isDefined) {
            if (posActiveTank._2 - 1 - i > posActiveTank._2 - 1) {
              obsList = matchfield.mvector(posActiveTank._1)(posActiveTank._2 - 1 - i).cobstacle.get :: obsList
            }
          }
          if (matchfield.mvector(posActiveTank._1)(posActiveTank._2 - 1 - i).containsThisTank.isDefined) {
            obsList
          }
        }
        obsList
      case "down" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", "down")
            Some(i)
          }
          case None => None
        }
        for (i <- (posActiveTank._2 + 1) to posPassiveTank._2) {
          if (matchfield.mvector(posActiveTank._1)(posActiveTank._2 + 1 + i).cobstacle.isDefined) {
            obsList = matchfield.mvector(posActiveTank._1)(posActiveTank._2 + 1 + i).cobstacle.get :: obsList
          }
          if (matchfield.mvector(posActiveTank._1)(posActiveTank._2 + 1 + i).containsThisTank.isDefined) {
            obsList
          }
        }
        obsList
      case "left" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", "left")
            Some(i)
          }
          case None => None
        }
        for (i <- (posActiveTank._1 - 1) to posPassiveTank._1 by -1) {
          if (matchfield.mvector(posActiveTank._1 - 1 - i)(posActiveTank._2).cobstacle.isDefined) {
            obsList = matchfield.mvector(posActiveTank._1 - 1 - i)(posActiveTank._2).cobstacle.get :: obsList
          }
          if (matchfield.mvector(posActiveTank._1 - 1 - i)(posActiveTank._2).containsThisTank.isDefined) {
            obsList
          }
        }
        obsList
      case "right" =>
        GameStatus.activeTank = GameStatus.activeTank match {
          case Some(i) => {
            attributeHandler.setAttribute(i, "facing", "right")
            Some(i)
          }
          case None => None
        }
        for (i <- (posActiveTank._1 + 1) to posPassiveTank._1 - 1) {
          if (matchfield.mvector(posActiveTank._1 + 1 + i)(posActiveTank._2).cobstacle.isDefined) {
            obsList = matchfield.mvector(posActiveTank._1 + 1 + i)(posActiveTank._2).cobstacle.get :: obsList
          }
          if (matchfield.mvector(posActiveTank._1 + 1 + i)(posActiveTank._2).containsThisTank.isDefined) {
            obsList
          }
        }
        obsList
      case _ => obsList
    }
    obsList
  }

  def calcHitChance(distance: Int, List: List[Obstacle]): Int = {
    var obstacleMalus = 0
    List.foreach(n => obstacleMalus += n.hitmalus)
    val hitchance = attributeHandler.getAttribute(GameStatus.activeTank.get, "accuracy").toInt - (distance * 5) - obstacleMalus
    if (hitchance > 0) {
      hitchance
    } else {
      0
    }
  }
}
