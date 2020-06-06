package tankcommander.model

import tankcommander.gameState.GameStatus
import tankcommander.model.girdComponent.GameFieldInterface
import tankcommander.model.girdComponent.gridBaseImpl._

object GameFieldFactory {
  // our 'factory' method
  def apply(s: String): GameFieldInterface = {
    if (s == "Map 1") return new Map1
    if (s == "Map 2") return new Map2
    new Map1
  }

  private case class GameField(mvector: Vector[Vector[Cell]] = Vector.tabulate(11, 11)((i, j) => Cell(pos = (i, j)))) extends GameFieldInterface {
    override val gridsX = 11
    override val gridsY = 11


    override def displayField(field: String = "", pos: (Int, Int) = (0, 0)): String = {
      var gamefield = field match {
        case _ if (mvector(pos._1)(pos._2).containsThisTank.isDefined) => field.concat("T" + "  ")
        case _ if ((mvector(pos._1)(pos._2).cobstacle.isDefined)
          && (mvector(pos._1)(pos._2).containsThisTank.isEmpty))
        => field.concat(mvector(pos._1)(pos._2).cobstacle.get.shortName + "  ")
        case _ if ((mvector(pos._1)(pos._2).cobstacle.isEmpty)
          && (mvector(pos._1)(pos._2).cobstacle.isEmpty)) => field.concat("o" + "  ")
      }
      if (pos._1 == gridsX - 1 && pos._2 == gridsY - 1) {
        gamefield = gamefield.concat("\n" + "HS: " + GameStatus.currentHitChance + "\n")
        return gamefield
      }
      if (pos._1 < gridsX - 1) {
        displayField(gamefield, (pos._1 + 1, pos._2))
      }
      else if (pos._2 < gridsY - 1) {
        gamefield = gamefield.concat("\n")
        displayField(gamefield, (0, pos._2 + 1))
      }
      else ""
    }

    override def update(vector: Vector[Vector[Cell]]): GameFieldInterface = {
      val newGamefield = GameField(mvector = vector)
      newGamefield
    }

    override def deepCopy: GameFieldInterface = {
      val gameFieldClone: GameField = GameField()
      //      for (x <- 0 until gridsX) {
      //        for (y <- 0 until gridsY) {
      //          gameFieldClone.mvector(x)(y) = this.mvector(x)(y).deepClone()
      //        }
      //      }
      gameFieldClone
    }

    override def toHtml: String = {
      val html = "<p>" + displayField().replaceAll("\n", "<br>") + "</p>"
      html
    }

  }

  private class Map1() extends GameField {

    //first row
    private val listBush = Vector((0, 0), (1, 0), (9, 0), (10, 0),
      //second row
      (0, 1), (1, 1), (9, 1), (10, 1), (0, 9), (1, 9),
      //third row
      (9, 9), (10, 9), (0, 10), (1, 10), (9, 10), (10, 10),
      //rest
      (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6)
    )
    private val listForest = Vector((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7),
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))
    private val listRock = Vector((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))
    private val listWater = Vector((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))
    private val listHill = Vector((5, 4), (5, 5), (5, 6))
    println(listHill)
    override val mvector: Vector[Vector[Cell]]
    = Vector.tabulate(gridsX, gridsY)((i, j) => Cell(pos = (i, j), cobstacle = this
    match {
      case _ if listBush.contains((i, j)) => Some(new Bush)
      case _ if listForest.contains((i, j)) => Some(new Forest)
      case _ if listHill.contains((i, j)) => Some(new Hill)
      case _ if listRock.contains((i, j)) => Some(new Rock)
      case _ if listWater.contains((i, j)) => Some(new Water)
      case _ => None
    }))
  }


  private class Map2() extends GameField {
    //first row
    private val listBush = Vector((4, 4), (6, 4), (4, 5), (6, 5))
    private val listForest = Vector((0, 0), (10, 0), (1, 1), (9, 1), (2, 2), (8, 2), (3, 3), (7, 3), (3, 6),
      (7, 6), (2, 7), (8, 7), (1, 8), (9, 8), (0, 9), (10, 9))
    private val listRock = Vector((5, 0), (5, 1), (5, 2), (5, 7), (5, 8), (5, 9))
    private val listWater = Vector((1, 3), (1, 4), (1, 5), (1, 6), (9, 3), (9, 4), (9, 5), (9, 6))

    override val mvector: Vector[Vector[Cell]]
    = Vector.tabulate(gridsX, gridsY)((i, j) => Cell(pos = (i, j),
      cobstacle = this.mvector(i)(j).cobstacle match {
        case _ if listBush.contains((i, j)) => Some(new Bush)
        case _ if listForest.contains((i, j)) => Some(new Forest)
        case _ if listRock.contains((i, j)) => Some(new Rock)
        case _ if listWater.contains((i, j)) => Some(new Water)
      }))
  }

}
