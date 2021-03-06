package tank.tankModelComponent

import play.api.libs.json.{JsValue, Json}

case class TankModel(tankBaseDamage: Int = 10
                     , accuracy: Int = 100
                     , hp: Int = 100
                     , posC: (Int, Int) = (0, 0)
                     , facing: String = "up") {

  def toJson: JsValue = Json.toJson(this)
  def fromJson(jsValue: JsValue): TankModel = jsValue.validate[TankModel].get

}

object TankModel {

  import play.api.libs.json._

  implicit val tankModelWrites: OWrites[TankModel] = Json.writes[TankModel]
  implicit val tankModelReads: Reads[TankModel] = Json.reads[TankModel]
}
