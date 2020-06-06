import controller.PlayerControllerInterface
import controller.baseImpl.PlayerController
import http.HttpServer
import playerComponent.Player

object PlayerMain {
  val controller: PlayerControllerInterface = PlayerController(Player("1"), Player("2"))
  val webserver = new HttpServer(controller)

  def main(args: Array[String]): Unit = {
    println("TankModule started...")
  }
}
