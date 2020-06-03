package controller

trait PlayerControllerInterface {
  def initPlayer(name: String): Unit

  def save(): Unit

  def load(): Unit

  def playerName: String
}
