package controller

trait TankModelControllerInterface {
  def initPlayer(name: String): Unit

  def save(): Unit

  def load(): Unit

  def playerName: String
}
