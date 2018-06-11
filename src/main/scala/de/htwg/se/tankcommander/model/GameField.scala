package de.htwg.se.tankcommander.model

case class GameField() {
  val gridsize_x = 11
  val gridsize_y = 11
  var matchfieldarray = Array.ofDim[Cell](gridsize_x, gridsize_y)
}
