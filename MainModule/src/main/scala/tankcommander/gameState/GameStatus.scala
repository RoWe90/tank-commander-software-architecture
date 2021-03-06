package tankcommander.gameState

import tankcommander.util.AttributeHandler


case class GameStatus() {
  var activePlayer: Option[String] = GameStatus.activePlayer match {
    case Some(i) => Some(i)
    case None => None
  }
  var passivePlayer: Option[String] = GameStatus.passivePlayer match {
    case Some(i) => Some(i)
    case None => None
  }
  var activeTank: Option[Int] = GameStatus.activeTank match {
    case Some(i) => Some(i)
    case None => None
  }
  var passiveTank: Option[Int] = GameStatus.passiveTank match {
    case Some(i) => Some(i)
    case None => None
  }
  var movesLeft: Boolean = GameStatus.movesLeft
  var currentPlayerActions: Int = GameStatus.currentPlayerActions
  var currentHitChance: Int = GameStatus.currentHitChance
}
object GameStatus {
  var activePlayer: Option[String] = None
  var passivePlayer: Option[String] = None
  var activeTank: Option[Int] = None
  var passiveTank: Option[Int] = None
  var movesLeft: Boolean = true
  var currentPlayerActions: Int = 2
  var currentHitChance: Int = 0
  var attributeHandler = new AttributeHandler

  def changeActivePlayer(): Unit = {
    val temp = activePlayer
    val temp2 = activeTank
    activePlayer = passivePlayer
    passivePlayer = temp
    activeTank = passiveTank
    passiveTank = temp2
    currentPlayerActions = 2
    movesLeft = true
  }

  def updatePlayers(): Unit = {
    activePlayer = Some(attributeHandler.getPlayerHttp(activeTank.get))
    passivePlayer  = Some(attributeHandler.getPlayerHttp(passiveTank.get))
  }

  def restoreGameStatus(gameStatusBackUp: GameStatus): Unit = {
    this.activePlayer = gameStatusBackUp.activePlayer match {
      case Some(i) => Some(i)
      case None => None
    }
    this.passivePlayer = gameStatusBackUp.passivePlayer match {
      case Some(i) => Some(i)
      case None => None
    }
    this.activeTank = gameStatusBackUp.activeTank match {
      case Some(i) => Some(i)
      case None => None
    }
    this.passiveTank = gameStatusBackUp.passiveTank match {
      case Some(i) => Some(i)
      case None => None
    }
    this.movesLeft = gameStatusBackUp.movesLeft
    this.currentPlayerActions = gameStatusBackUp.currentPlayerActions
    this.currentHitChance = gameStatusBackUp.currentHitChance
  }

  def increaseTurns(): Unit = {
    GameStatus.currentPlayerActions -= 1
    if (GameStatus.currentPlayerActions == 0) {
      GameStatus.movesLeft = false
    }
  }

  def endGame(): Unit = {
    GameStatus.activePlayer match {
      case Some(i) => print(i + " Won\n")
      case None => print("Error couldn't get Winner")
    }
  }

  def resetGameStatus(): Unit = {
    GameStatus.activePlayer = None
    GameStatus.passivePlayer = None
    GameStatus.activeTank = None
    GameStatus.passiveTank = None
    GameStatus.movesLeft = true
    GameStatus.currentPlayerActions = 2
    GameStatus.currentHitChance = 0
  }

}

