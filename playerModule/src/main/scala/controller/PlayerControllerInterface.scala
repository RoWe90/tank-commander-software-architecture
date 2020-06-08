package controller

import playerComponent.Player

trait PlayerControllerInterface {

  var player1: Player
  var player2: Player

  def initPlayer(name: String, whichPlayer: Int): Unit

  //def save(): Unit

 // def load(): Unit

  def playerNameList: List[String]
}
