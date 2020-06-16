package tank.tankModelComponent.slickComponent

trait SlickInterface {

  def saveTank(tankBaseDamage: Int, accuracy: Int, hp: Int, posC: (Int, Int), facing: String) : Unit

  def loadTank(which: Int) : (Int, Int, Int, (Int, Int), String)

}
