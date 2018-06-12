package de.htwg.se.tankcommander.obsolete

trait PowerUP {
  val name: String
  val duration: Integer
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
