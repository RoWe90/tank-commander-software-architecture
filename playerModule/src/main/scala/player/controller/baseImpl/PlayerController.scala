package player.controller.baseImpl

import com.google.inject.{Guice, Inject, Injector}
import player.PlayerModelModule
import player.controller.PlayerControllerInterface
import player.playerComponent.Player
import player.playerComponent.daoComponent.DAOInterface

import scala.util.{Failure, Success}

case class PlayerController @Inject() (var player1: Player, var player2: Player) extends PlayerControllerInterface {

  val injector: Injector = Guice.createInjector(new PlayerModelModule)
  val db: DAOInterface = injector.getInstance(classOf[DAOInterface])

  override def initPlayer(name: String, whichPlayer: Int): Unit = {
    whichPlayer match {
      case 1 => player1 = new Player(name)
      case 2 => player2 = new Player(name)
      case _ =>
    }
  }

  override def save(): Unit = {
    db.savePlayer(1, player1.name)
    db.savePlayer(2, player2.name)
  }

  override def load(): Unit = {
    val result0 = db.loadPlayer(1)
    player1 = Player(result0)
    val result1 = db.loadPlayer(2)
    player2 = Player(result1)
  }

  override def playerNameList: List[String] = List(player1.toString, player2.toString)

}
