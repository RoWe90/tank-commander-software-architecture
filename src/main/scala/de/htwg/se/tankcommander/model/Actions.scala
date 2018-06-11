package de.htwg.se.tankcommander.model

//noinspection ScalaStyle
class Actions {

  /*
    def lineOfSightContainsTank(tankModel: TankModel, spielfeld: GameField): (Boolean, Int, Int) = {
      val sy:Int = spielfeld.gridsize_y
      val sx:Int = spielfeld.gridsize_x
      val ty:Int = tankModel.getPositionAsIntY()
      val tx:Int = tankModel.getPositionAsIntX()

      tankModel.facing match {
        case "up" =>
          for (i <- ty to sy) if (spielfeld.matchfieldarray(tx)(i).containsThisTank != null) {
            (true, tx, i)
          }
        case "down" =>
          for (i <- sy to ty by -1) if (spielfeld.matchfieldarray(tx)(i).containsThisTank != null) {
            (true, tx, i)
          }
        case "left" =>
          for (i <- sy to ty by -1) if (spielfeld.matchfieldarray(i)(ty).containsThisTank != null) {
            (true, i, ty)
          }
        case "right" =>
          for (i <- sy to ty) if (spielfeld.matchfieldarray(i)(ty).containsThisTank != null) {
            (true, i, ty)
          }

      }
      false
    }
  */

  def calcDmgDone(gunner: TankModel, target: TankModel): Integer = {
    var dmg = gunner.tankBaseDamage
    dmg
  }

  /*
    def calcHitChance(gunner: TankModel, target: TankModel): Integer = {
      if (lineOfSightContainsTank()._1 == true) {
        var hitchance = 0;
      } else {
        0
      }
    }
  */
  def move(): Unit = {
    if (movePossible == true) {
      false
    }
  }

  def movePossible(): Boolean = {
    false
  }

  /*
    def useItem(tankModel: TankModel, input: String): Unit = {
      tankModel.tankInventory.containsItem(input) match {
        case true =>
          input match {
            case "Kaffee" =>
            case "Reparaturkit" =>
            case "Zielwasser" =>
          }
          print("Item nicht im Inventar")
      }
    }
  */


}
