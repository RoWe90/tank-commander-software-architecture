package gridComponent.gridBaseImpl

case class Cell(pos: (Int, Int), cobstacle: Option[Obstacle] = None, containsThisTank: Option[TankModel] = None) {
  val x: Int = pos._1
  val y: Int = pos._2

}