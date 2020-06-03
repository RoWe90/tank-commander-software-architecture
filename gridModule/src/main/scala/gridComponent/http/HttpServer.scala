package gridComponent.http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

class HttpServer(){

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route: Route = concat(
    get {
      path("grid") {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<p> Welcome to TankCommander </p>"))
      } ~
        path("grid" / "gamefield") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "test"))
        }
    },
    put {
      path("grid" / Segment) {
        command => {
          val response = "Command: " + command + "</br>" + "Field:</br>" + command
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, response))
        }
      }
    }
  )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)


  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }


}