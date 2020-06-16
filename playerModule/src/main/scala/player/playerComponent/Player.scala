package player.playerComponent

import play.api.libs.json.{JsValue, Json}

case class Player(name: String) {
  override def toString: String = name

  def toJson: JsValue = Json.toJson(this.toString)
  def fromJson(jsValue: JsValue): String = jsValue.validate[String].get

}
