package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import java.io.{BufferedWriter, FileWriter, PrintWriter}
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.GameField
import net.liftweb.json.Serialization.write
import play.api.libs.json._
import scala.collection.mutable._
import scala.io.Source
import scala.reflect.io.File
import java.io._

object SaveGame {
  val json: JsValue = JsObject(Seq(
    "name" -> JsString("Watership Down"),
    "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
    "residents" -> JsArray(IndexedSeq(
      JsObject(Seq(
        "name" -> JsString("Fiver"),
        "age" -> JsNumber(4),
        "role" -> JsNull
      )),
      JsObject(Seq(
        "name" -> JsString("Bigwig"),
        "age" -> JsNumber(6),
        "role" -> JsString("Owsla")
      ))
    ))
  ))
  var test: GameField = null

  def saveGame: Unit = {
    val file = new File("C:\\Users\\Sebastian\\Desktop\\ThisIsATest.txt")
    val bw = new BufferedWriter(new FileWriter(file))
    pw.write("Hello, world")
    val jsonString = write(p)
    println(jsonString)
    bw.write(text)
    bw.close()
  }

  Some(new PrintWriter("foo.txt")).foreach { p =>
    p.write(JSONObject(map.toMap).toString()); p.close
  }

  def loadGame: Unit = {
    val filename = "thisfile.json"
    for (line <- Source.fromFile(filename).getLines()) {
      println(line)
    }
  }
}
