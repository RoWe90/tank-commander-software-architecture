package de.htwg.se.tankcommander.model.gridComponent
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.{Cell, GameField}

trait GameFieldInterface {
  val gridsX: Int
  val gridsY: Int
  var marray: Array[Array[Cell]]

  def fillGameFieldWithCells(): Unit

  def fillGameFieldCellsWithObstacles(): Unit

  def toString: String

  def deepCopy: GameField
}
