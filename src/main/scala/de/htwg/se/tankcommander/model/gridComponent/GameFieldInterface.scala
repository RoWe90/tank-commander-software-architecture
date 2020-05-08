package de.htwg.se.tankcommander.model.gridComponent

import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.Cell

trait GameFieldInterface {
  val gridsX: Int
  val gridsY: Int
  val mvector: scala.collection.immutable.Vector[scala.collection.immutable.Vector[Cell]]

  def fillGameFieldWithCells(): Unit

  def fillGameFieldCellsWithObstacles(): Unit

  def copy(x : Int,y:Int,vector: scala.collection.immutable.Vector[scala.collection.immutable.Vector[Cell]]): GameFieldInterface

  def toString: String
}
