package player.playerComponent.slickComponent

trait SlickInterface {

  def savePlayer(name: String) : Unit

  def loadPlayer(which: Int) : String

}
