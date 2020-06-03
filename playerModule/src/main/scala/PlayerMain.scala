import controller.PlayerControllerInterface
import controller.baseImpl.PlayerController
import http.HttpServer
import model.FileIOInterface
import model.FileIOJSON.FileIO
import playerComponent.Player

object PlayerMain {
  val controller = PlayerController(new Player("Anon"), new FileIO)
  val webserver = new HttpServer(controller)

  def main(args: Array[String]): Unit = {}
}
