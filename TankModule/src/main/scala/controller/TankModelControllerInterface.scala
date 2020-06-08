package controller

trait TankModelControllerInterface {
   def initTank(whichTank: Int): Unit

  def getTankBaseDamage(whichTank: Int): Int
  def getTankAccuracy(whichTank: Int): Int
  def getTankHp(whichTank: Int): Int
  def getTankPosC(whichTank: Int): (Int, Int)
  def getTankFacing(whichTank: Int): String

  def setTankBaseDamage(whichTank: Int, tankBaseDamage: Int): Unit
  def setTankAccuracy(whichTank: Int, accuracy: Int): Unit
  def setTankHp(whichTank: Int, hp: Int): Unit
  def setTankPosC(whichTank: Int, posC: (Int, Int)): Unit
  def setTankFacing(whichTank: Int, facing: String): Unit

}
