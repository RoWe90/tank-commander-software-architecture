package tankcommander.view.http

import tankcommander.controllerComponent.ControllerInterface
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer

class HttpServer(controller: ControllerInterface){

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  val route: Route = concat(
    get {
      path("tankcommander") {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<p> Welcome to TankCommander </p>"))
      } ~
        path("tankcommander" / "gamefield") {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, gamefieldToHtml))
        }
    },
    put {
      path("tankcommander" / Segment) {
        command => {
          processInputLine(command)
          val response = "Command: " + command + "</br>" + "Field:</br>" + gamefieldToHtml
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, response))
        }
      }
    }
  )

  val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  def gamefieldToHtml: String = {
    controller.gamefieldToHtml
  }

  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def processInputLine(command: String): Unit = {
    command.toLowerCase match {
      case "start" => print("Das Spiel startet, macht euch bereit" + "\n")
        controller.setUpGame()
      case "exit" => unbind()
      case "end turn" => controller.endTurnChangeActivePlayer()
      case "undo" => controller.undo()
      case "redo" => controller.redo()
      case "save" => controller.save()
      case "load" => controller.load()
      case _ => if (!controller.checkIfPlayerHasMovesLeft())
        println("no turns left")
      case _ =>
    }
    if (controller.checkIfPlayerHasMovesLeft()) {
      command.toLowerCase match {
        case "up" => controller.move(command)
        case "down" => controller.move(command)
        case "left" => controller.move(command)
        case "right" => controller.move(command)
        case "shoot" => controller.shoot()
        case _ =>
      }
    }
  }

}