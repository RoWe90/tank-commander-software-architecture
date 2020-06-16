package player.http

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import player.controller.PlayerControllerInterface
import play.api.libs.json.Json

import scala.concurrent.Future

class HttpServer(playerController: PlayerControllerInterface){

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route: Route = concat(
      get {
        path("player" / "player" / "1") {
          val container = playerController.playerNameList(0)
          complete(Json.toJson(container.replace("\"", "")).toString())
        }
    },
    get {
      path("player" / "player" / "2") {
        val container = playerController.playerNameList(1)
        complete(Json.toJson(container.replace("\"", "")).toString())
      }
    },
    get {
      path("player" / "player" / "save") {
        playerController.save()
        println("Saved to DB")
        complete("")
      }
    },
    get {
      path("player" / "player" / "load") {
        playerController.load()
        println("Loaded from DB")
        complete("")
      }
    },
    post {
      path("player" / "player") {
            decodeRequest {
              entity(as[String]) { string => {
                println(string)
                val name = (Json.parse(string) \ "name").get.toString
                val which = (Json.parse(string) \ "which").get.toString.toInt
                playerController.initPlayer(name, which)
                println("Player "+ which +" created: " + name)
                complete("")
              }
              }
            }
      }
    }
  )

  val bindingFuture : Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 54251)


  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }


}