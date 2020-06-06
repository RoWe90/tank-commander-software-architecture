package http

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import controller.TankModelControllerInterface
import play.api.libs.json.Json

import scala.concurrent.{ExecutionContextExecutor, Future}

class HttpServer(tankModelController: TankModelControllerInterface) {

  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val route: Route = concat(
    get {
      path("tank" / "1") {
        val container = playerController.playerNameList(0)
        complete(Json.toJson(container.replace("\"", "")).toString())
      }
    },
    get {
      path("tank" / "2") {
        val container = playerController.playerNameList(1)
        complete(Json.toJson(container.replace("\"", "")).toString())
      }
    },
    post {
      path("tank") {
        decodeRequest {
          entity(as[String]) { string => {
            println(string)
            val which = (Json.parse(string) \ "which").get.toString.toInt
            val attribut = (Json.parse(string) \ "attribut").get.toString
            val value = (Json.parse(string) \ "value").get.toString

            which match {
              case 1 =>
              case 2 =>
              case _ => println("Falscher Spieler angegeben")
            }



            println("Changed Tank"+ which + " " + attribut + " to " + value)
            complete("")
          }
          }
        }
      }
    }
  )

  val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(route, "localhost", 8080)


  def unbind(): Unit = {
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }

  def changeAttribute(tank: String, attribute: String, value: String): Unit = {
    attribute match {
      case tankBaseDamage => tankModelController.setTankBaseDamage(tank.toInt, value.toInt)
      case hp => tankModelController.setTankHp(tank.toInt, value.toInt)
      case accuracy =>
      case posC =>
      case facing =>
    }
  }



}