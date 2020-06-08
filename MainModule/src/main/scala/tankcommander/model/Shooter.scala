package tankcommander.model

import tankcommander.gameState.GameStatus
import tankcommander.util.AttributeHandler


class Shooter {

  val attributeHandler = AttributeHandler()

  def shoot(): Unit = {
    if (GameStatus.currentHitChance > 0) {
      if (simShot()) {
        GameStatus.endGame()
      }
      GameStatus.increaseTurns()
    } else {
      print("No Target in sight\n")
    }
  }

  def simShot(): Boolean = {
    val r = new scala.util.Random
    val r1 = r.nextInt(100)
    if (GameStatus.currentHitChance >= r1) {
      val dmg = attributeHandler.getAttribute(GameStatus.activeTank.get, "tankBaseDamage").toInt + 40
      dealDmgTo(dmg)
      print("You did: " + dmg + " dmg\n")
      if (attributeHandler.getAttribute(GameStatus.activeTank.get, "hp").toInt <= 0) {
        return true
      }
      false
    } else {
      print("Sadly you missed...\n")
      false
    }
  }

  def dealDmgTo(dmg: Int): Unit = {
    GameStatus.passiveTank = GameStatus.passiveTank match {
      case Some(i) => {
        var hp = attributeHandler.getAttribute(i, "hp").toInt
        hp = hp - dmg
        attributeHandler.setAttribute(i, "hp", hp.toString)
        Some(i)
      }
      case None => None
    }
    if (attributeHandler.getAttribute(GameStatus.passiveTank.get, "hp").toInt <= 0) {
      GameStatus.endGame()
    }
  }

}
