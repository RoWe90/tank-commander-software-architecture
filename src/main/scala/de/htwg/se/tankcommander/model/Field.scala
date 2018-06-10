package de.htwg.se.tankcommander.model

class Field {
  val gridsize_x = 10
  val gridsize_y = 10
  var matchfieldarray = Array.ofDim[Cell](gridsize_x, gridsize_y)
  fillField(this)

  def setPositionTank(xtank: Integer, ytank: Integer, tank1: TankModel): Unit = {

    if (tank1.position.x != null && tank1.position.y != null) {
      matchfieldarray(tank1.position.x)(tank1.position.y).containsThisTank = null
      tank1.position(null)
      tank1.position(matchfieldarray(xtank)(ytank))
      matchfieldarray(xtank)(ytank).containsThisTank = tank1
    }
  }


}
