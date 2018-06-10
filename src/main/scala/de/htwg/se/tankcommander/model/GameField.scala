package de.htwg.se.tankcommander.model

class GameField() {
  val gridsize_x = 10
  val gridsize_y = 10
  var matchfieldarray = Array.ofDim[Cell](gridsize_x, gridsize_y)

}
