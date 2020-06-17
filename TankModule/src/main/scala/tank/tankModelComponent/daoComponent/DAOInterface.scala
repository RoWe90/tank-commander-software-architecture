package tank.tankModelComponent.daoComponent

trait DAOInterface {

  def saveTank(name: String, tankBaseDamage: Int, accuracy: Int, hp: Int, posC: (Int, Int), facing: String) : Unit

  def loadTank(which: String) : (Int, Int, Int, (Int, Int), String)

}
