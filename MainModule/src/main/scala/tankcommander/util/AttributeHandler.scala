package tankcommander.util

import java.util.concurrent.TimeUnit

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.client.RequestBuilding.{Get, Post}
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.unmarshalling.Unmarshal
import akka.stream.ActorMaterializer
import play.api.libs.json.Json

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, ExecutionContextExecutor}

case class AttributeHandler() {

  implicit val system: ActorSystem = ActorSystem()
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  def getPlayerHttp(which: Int): String = {
    val response = Http().singleRequest(Get("http://localhost:54251/player/player/" + which))
    val playerFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    val playerString = Await.result(playerFuture, Duration(1, TimeUnit.SECONDS))

    playerString.replace("\"", "")
  }

  def setPlayerHttp(name: String, which: Int): Unit = {
    Http().singleRequest(Post("http://localhost:54251/player/player",
      HttpEntity(ContentTypes.`application/json`, Json.obj("name" -> name, "which" -> which).toString())))
  }

  def setAttribute(which: Int, attribute: String, value: String): Unit = {
    Http().singleRequest(Post("http://localhost:54252/tank",
      HttpEntity(ContentTypes.`application/json`, Json.obj("which" -> which, "attribute" -> attribute, "value" -> value).toString())))
  }

  def getPosC(which: Int): (Int, Int) = {
    val posCString = getAttribute(which, "posC")
    val posCWithoutBraces = posCString.replace("(", "").replace(")", "").replace("\"", "")
    val posCArray = posCWithoutBraces.split(",")

    (posCArray(0).toInt, posCArray(1).toInt)
  }

  def getAttribute(which: Int, attribute: String): String = {
    val response = Http().singleRequest(Get("http://localhost:54252/tank/" + which + "/" + attribute))
    val tankFuture = response.flatMap(r => Unmarshal(r.entity).to[String])
    val tankString = Await.result(tankFuture, Duration(1, TimeUnit.SECONDS))

    tankString.replace("\"", "")
  }


}
