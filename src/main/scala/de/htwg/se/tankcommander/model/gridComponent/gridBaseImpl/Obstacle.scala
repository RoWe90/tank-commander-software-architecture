package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl


trait Obstacle {
  val name: String
  val desc: String
  val shortName: String
  val passable: Boolean
  val hitmalus: Int
  val imagePath: String
  def deepClone(): Obstacle

}

class Bush extends Obstacle {
  override val name: String = "Gebüsche"
  override val desc: String = "Gebüsche verringern die Präzision von Schüssen"
  override val passable: Boolean = true
  override val shortName: String = "B"
  override val hitmalus: Int = 10
  override val imagePath: String = "src/main/ressources/icons/bush.png"
  override def deepClone(): Obstacle = new Bush

}

class Hill extends Obstacle {
  override val name: String = "Hügel"
  override val desc: String = "Ermöglichen direkten Beschuss mapweit"
  override val passable: Boolean = true
  override val shortName: String = "H"
  override val hitmalus: Int = 20
  override val imagePath: String = "src/main/ressources/icons/mountain.png"
  override def deepClone(): Obstacle = new Hill

}

class Rock extends Obstacle {
  override val name: String = "Stein"
  override val desc: String = "Steine dienen als Hinderniss und können weder passiert noch durschossen werden"
  override val passable: Boolean = false
  override val shortName: String = "S"
  override val hitmalus: Int = 100
  override val imagePath: String = "src/main/ressources/icons/rock.png"
  override def deepClone(): Obstacle = new Rock

}

class Forest extends Obstacle {
  override val name: String = "Wald"
  override val desc: String = "Wälder liefern Schutz und verringern die Hitchance des Gegners"
  override val passable: Boolean = true
  override val shortName: String = "F"
  override val hitmalus: Int = 10
  override val imagePath: String = "src/main/ressources/icons/tree.png"
  override def deepClone(): Obstacle = new Forest

}

class Water extends Obstacle {
  override val name: String = "Wasser"
  override val desc: String = "Wasser kann nicht passiert werden"
  override val passable: Boolean = false
  override val shortName: String = "W"
  override val hitmalus: Int = 0
  override val imagePath: String = "src/main/ressources/icons/water.png"
  override def deepClone(): Obstacle = new Water

}



