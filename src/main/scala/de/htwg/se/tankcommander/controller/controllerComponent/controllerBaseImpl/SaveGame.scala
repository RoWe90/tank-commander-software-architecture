package de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl

import java.io.{BufferedWriter, FileWriter, PrintWriter}
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{Cell, GameField}
import net.liftweb.json.Serialization.write
import play.api.libs.json._

import scala.collection.mutable._
import scala.io.Source
import scala.reflect.io.File
import java.io._
import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import net.liftweb.json.DefaultFormats
import scala.tools.nsc.doc.html.page.JSONObject

class SaveGame(controller: Controller) {
  def saveGame: Unit = {
    implicit val formats = DefaultFormats
    val list = controller.matchfield.marray.toList
    val jsonString = write(list)
    println(jsonString)
    val list2 = GameStatus
    val jsonString2 = write(list2)
    println(jsonString2)
  }

  def loadGame: Unit = {
    val filename = "thisfile.json"
    for (line <- Source.fromFile(filename).getLines()) {
      println(line)
    }
  }
}

import java.io._

