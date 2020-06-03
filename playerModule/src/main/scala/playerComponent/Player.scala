package playerComponent

import play.api.libs.json.{JsValue, Json}

case class Player(name: String) {
  override def toString: String = name

  def toJson: JsValue = Json.toJson(this)
  def fromJson(jsValue: JsValue): Player = jsValue.validate[Player].get

  def initPlayer(name: String): Unit = {
    Player(name)
 }
}
object Player {
  import play.api.libs.json._
  implicit val playerWrites: OWrites[Player] = Json.writes[Player]
  implicit val playerReads: Reads[Player] = Json.reads[Player]
}