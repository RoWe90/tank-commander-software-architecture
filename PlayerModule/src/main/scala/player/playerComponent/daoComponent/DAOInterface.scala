package player.playerComponent.daoComponent

trait DAOInterface {

  def savePlayer(which: Int, name: String) : Unit

  def loadPlayer(which: Int) : String

}
