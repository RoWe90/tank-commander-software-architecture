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

class DMGUP extends PowerUP {
  override val name: String = "Placeholder"
  override val duration: Integer = 2
}

class ImpassableTerrainUP extends PowerUP {
  override val name: String = "Placeholder"
  override val duration: Integer = 2
}

class ShieldUP extends PowerUP {
  override val name: String = "Placeholder"
  override val duration: Integer = 2
}

class TurnsUP extends PowerUP {
  override val name: String = "Placeholder"
  override val duration: Integer = 2
}
