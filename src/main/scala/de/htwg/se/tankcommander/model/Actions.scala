package de.htwg.se.tankcommander.model

import de.htwg.se.tankcommander.util.Observer

//noinspection ScalaStyle
class Actions {


  object Actions extends Observer {
    var matchfield = new GameField

    //aktuelles Spielfeld ziehen
    def update(): Unit = {
      //matchfield = aktualisiertes matchfield
    }

    def lineOfSightContainsTank(gunner: TankModel, spielfeld: GameField): (Boolean, Int, Int, Int) = {
      val sy: Int = spielfeld.gridsize_y
      val sx: Int = spielfeld.gridsize_x
      val ty: Int = gunner.getPositionAsIntY()
      val tx: Int = gunner.getPositionAsIntX()

      gunner.facing match {
        case "up" =>
          for (i <- ty to sy) if (spielfeld.matchfieldarray(tx)(i).containsThisTank != null) {
            (true, tx, i, calcHitChance(gunner, spielfeld.matchfieldarray(tx)(i).containsThisTank))
          }
        case "down" =>
          for (i <- sy to ty by -1) if (spielfeld.matchfieldarray(tx)(i).containsThisTank != null) {
            (true, tx, i, calcHitChance(gunner, spielfeld.matchfieldarray(tx)(i).containsThisTank))
          }
        case "left" =>
          for (i <- sy to ty by -1) if (spielfeld.matchfieldarray(i)(ty).containsThisTank != null) {
            (true, i, ty, calcHitChance(gunner, spielfeld.matchfieldarray(i)(ty).containsThisTank))
          }
        case "right" =>
          for (i <- sy to ty) if (spielfeld.matchfieldarray(i)(ty).containsThisTank != null) {
            (true, i, ty, calcHitChance(gunner, spielfeld.matchfieldarray(i)(ty).containsThisTank))
          }

      }
      (false, 0, 0, 0)
    }

    def calcHitChance(gunner: TankModel, target: TankModel): Integer = {
      var hitchance = 0;

    }

    def calcDmgDone(gunner: TankModel, target: TankModel): Integer = {
      var dmg = gunner.tankBaseDamage
      takeDmg(target, dmg)
      dmg
    }

    def takeDmg(target: TankModel, dmg: Int): Unit = {
      target.healthpoints = target.healthpoints - dmg
    }

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

}
