package de.htwg.se.tankcommander

import de.htwg.se.tankcommander.model.Player

object TankCommander {
  def main(args: Array[String]): Unit = {
    val student = Player("Your Name")
    println("Hello Commander, " + student.name)
  }
}
