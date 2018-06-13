package de.htwg.se.tankcommander.model

trait Obstacle {
  val name: String
  val desc: String
  val shortName: String
  val passable: Boolean
  val hitmalus: Int

}

class Bush extends Obstacle {
  val name: String = "Gebüsche"
  val desc: String = "Gebüsche verringern die Präzision von Schüssen"
  val passable: Boolean = true
  val shortName: String = "B"
  override val hitmalus: Int = 10
}

class Hill extends Obstacle {
  val name: String = "Hügel"
  val desc: String = "Ermöglichen direkten Beschuss mapweit"
  val passable: Boolean = false
  val shortName: String = "H"
  override val hitmalus: Int = 100
}

class Rock extends Obstacle {
  val name: String = "Stein"
  val desc: String = "Steine dienen als Hinderniss und können weder passiert noch durschossen werden"
  val passable: Boolean = false
  val shortName: String = "S"
  override val hitmalus: Int = 100
}

class Forest extends Obstacle {
  val name: String = "Wald"
  val desc: String = "Wälder liefern Schutz und verringern die Hitchance des Gegners"
  val passable: Boolean = true
  val shortName: String = "F"
  override val hitmalus: Int = 10
}

class Water extends Obstacle {
  val name: String = "Wasser"
  val desc: String = "Wasser kann nicht passiert werden"
  val passable: Boolean = false
  val shortName: String = "W"
  override val hitmalus: Int = 0
}


