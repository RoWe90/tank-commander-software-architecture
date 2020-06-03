package http

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import controller.PlayerControllerInterface
import controller.baseImpl.{InitPlayerContainer}
import play.api.libs.json.Json
import playerComponent.Player

import scala.concurrent.Future

class HttpServer(player: PlayerControllerInterface){

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route: Route = concat(
    get {
      path("player" / "save") {
        player.save()
        complete("")
      }},
      get {
        path("player" / "load") {
          player.load()
          complete("")
        }
      },
      get {
        path("player" / "player") {
          val container = Player(player.playerName)
          complete(Json.toJson(container).toString())
        }
    },
    post {
      path("player" / "player") {
            decodeRequest {
              entity(as[String]) { string => {
               val container = Json.fromJson(Json.parse(string))(InitPlayerContainer.containerReads).get
                player.initPlayer(container.name)
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