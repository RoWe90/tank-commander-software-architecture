package de.htwg.se.tankcommander.controller.controllerComponent

import play.api.libs.json.JsObject

trait FileIOInterface {
  def save: Unit

  def GameStateToJson: JsObject

  def load: Unit
}
