package de.htwg.se.tankcommander.model

// Temporary Buffs which are activated on cell contact. Stronger then items and upgrades but short lived

trait PowerUP {
  val name: String
  val duration: Integer

  //This should apply status buffs to player and return duration of powerup
  def activate(powerUP: PowerUP): Integer = {

    print()
    duration
  }
}

class DMGUP {

}

class ImpassableTerrainUP {

}

class ShieldUP {

}

class TurnsUP {

}
