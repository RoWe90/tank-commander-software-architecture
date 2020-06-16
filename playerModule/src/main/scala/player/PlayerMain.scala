package player

import player.controller.PlayerControllerInterface
import player.controller.baseImpl.PlayerController
import player.playerComponent.Player
import player.http.HttpServer

object PlayerMain {
  val controller: PlayerControllerInterface = PlayerController(Player("1"), Player("2"))
  val webserver = new HttpServer(controller)

  def main(args: Array[String]): Unit = {
    println("PlayerModule started...")
  }
}
