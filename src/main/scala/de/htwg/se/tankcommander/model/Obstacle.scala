package de.htwg.se.tankcommander.model

trait Obstacle {
  val name: String
  val desc: String
  val passable: Boolean


 class Bush{
   val name: String = "Gebüsche"
   val desc: String = ""
   val passable: Boolean = true
 }

  class Hill{
    val passable: Boolean = false
  }

  class Rock{
    val name: String = "Stein"
    val desc: String = ""
    val passable: Boolean = false
  }

  class Tree{
    val name: String = "Wald"
    val desc: String = ""
    val passable: Boolean = true
  }

  class Water{
    val name: String = "Wasser"
    val desc: String = ""
    val passable: Boolean = false
  }

}
