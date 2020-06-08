package tankcommander.model.girdComponent

import tankcommander.model.girdComponent.gridBaseImpl.Cell

trait GameFieldInterface extends Cloneable {
  val gridsX: Int
  val gridsY: Int
  val mvector: Vector[Vector[Cell]]

  //def fillGameFieldWithCells(): Unit

  //def fillGameFieldCellsWithObstacles(): Unit

  def update(vector: Vector[Vector[Cell]]): GameFieldInterface

  def displayField(field: String = "", pos: (Int, Int) = (0, 0)): String

  def deepCopy: GameFieldInterface

  def toHtml: String

}
