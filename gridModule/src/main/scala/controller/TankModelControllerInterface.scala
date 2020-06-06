package controller

trait TankModelControllerInterface {
  def initTank(tankBaseDamage: Int = 10
               , accuracy: Int = 100
               , hp: Int = 100
               , posC: (Int, Int) = (0, 0)
               , facing: String = "up"): Unit

  def save(): Unit

  def load(): Unit

  def playerName: String
}
