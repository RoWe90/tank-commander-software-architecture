package model.FileIOJSON

import java.io.{File, PrintWriter}

import model.FileIOInterface
import play.api.libs.json.Json
import playerComponent.Player

import scala.io.Source
import scala.util.Try

case class FileIO() extends FileIOInterface {
  override def save(player: Player, path: String): Try[Player] = {
    Try{
      /*
      val source = Source.fromFile(path + ".json")
      val string = source.getLines().mkString
      source.close()
      val json = Json.parse(string)
      player.fromJson(json)
       */
      player
    }
  }

  override def load(player: Player, path: String): Try[Unit] = {
    Try{
      /*
      val printWriter = new PrintWriter(new File(path + ".json"))
      printWriter.write(Json.prettyPrint(player.toJson))
      printWriter.close()
       */
    }
  }

}
