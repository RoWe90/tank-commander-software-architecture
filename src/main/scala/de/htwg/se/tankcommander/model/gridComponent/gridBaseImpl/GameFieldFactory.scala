package de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl

import de.htwg.se.tankcommander.controller.controllerComponent.GameStatus
import de.htwg.se.tankcommander.model.gridComponent.GameFieldInterface

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
    //override val mvector: Vector[Vector[Cell]] = Vector.tabulate(gridsX, gridsY)((i, j) => Cell(pos = (i, j))) //[Vector[Cell]]()//Array.ofDim[Cell](gridsX, gridsY)
    //fillGameFieldWithCells()
    //fillGameFieldCellsWithObstacles()

    //    override def copy(x: Int = this.gridsX, y: Int = this.gridsX, vector: Vector[Vector[Cell]]): GameFieldInterface = {
    //      val gamefield = new GameField
    //      gamefield
    //    }

    //    override def fillGameFieldWithCells(): Unit = {
    //      for (x <- 0 until gridsX) {
    //        for (y <- 0 until gridsY) {
    //
    //          //mvector(x)(y) = new Cell((x, y))
    //        }
    //      }
    //    }

    //override def fillGameFieldCellsWithObstacles(): Unit = {
    //}

    override def toString: String = {
      var output = ""
      for (z <- 0 until gridsY) {
        output = output.concat("\n")
        for (i <- 0 until gridsX) {
          if (mvector(i)(z).cobstacle.isDefined) {
            if (mvector(i)(z).containsThisTank.isDefined) {
              output = output.concat("T" + "  ")
            } else {
              output = output.concat(mvector(i)(z).cobstacle.get.shortName + "  ")
            }
          } else {
            if (mvector(i)(z).containsThisTank.isDefined) {
              output = output.concat("T" + "  ")
            } else {
              output = output.concat("o" + "  ")
            }
          }
        }
      }
      output = output.concat("HS: " + GameStatus.currentHitChance + "\n")
      output
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
  }

  private class Map1() extends GameField {

    //override def fillGameFieldCellsWithObstacles(): Unit = {
    //first row
    private val listBush = Vector((0, 0), (1, 0), (9, 0), (10, 0),
      //second row
      (0, 1), (1, 1), (9, 1), (10, 1), (0, 9), (1, 9),
      //third row
      (9, 9), (10, 9), (0, 10), (1, 10), (9, 10), (10, 10),
      //rest
      (3, 4), (3, 5), (3, 6), (7, 4), (7, 5), (7, 6)
    )
    //      listBush.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Bush))
    private val listForest = Vector((2, 2), (3, 2), (7, 2), (8, 2), (2, 3), (3, 3), (7, 3), (8, 3), (2, 7),
      (3, 7), (7, 7), (8, 7), (2, 8), (3, 8), (7, 8), (8, 8))
    //      listForest.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Forest))
    private val listRock = Vector((1, 4), (4, 3), (6, 3), (9, 4), (1, 6), (4, 7), (6, 7), (9, 6))
    //      listRock.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Rock))
    private val listWater = Vector((4, 0), (6, 0), (4, 2), (6, 2), (4, 8), (6, 8), (4, 10), (6, 10))
    //      listWater.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Water))
    private val listHill = Vector((5, 4), (5, 5), (5, 6))
    println(listHill)
    //      listHill.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Hill))
    override val mvector: Vector[Vector[Cell]]
    = Vector.tabulate(gridsX, gridsY)((i, j) => Cell(pos = (i, j), cobstacle = this
    match {
      case _ if listBush.contains((i, j)) => Option(new Bush)
      case _ if listForest.contains((i, j)) => Option(new Forest)
      case _ if listHill.contains((i, j)) => Option(new Hill)
      case _ if listRock.contains((i, j)) => Option(new Rock)
      case _ if listWater.contains((i, j)) => Option(new Water)
      case _ => None
    }))
  }

  //  override def deepCopy: GameFieldInterface = {
  //    val gameFieldClone: Map1 = new Map1
  //    for (x <- 0 until gridsX) {
  //      for (y <- 0 until gridsY) {
  //        gameFieldClone.mvector(x)(y) = this.mvector(x)(y).deepClone()
  //      }
  //    }
  //    gameFieldClone
  //  }


  private class Map2() extends GameField {
    //override def fillGameFieldCellsWithObstacles: Unit = {
    //first row
    private val listBush = Vector((4, 4), (6, 4), (4, 5), (6, 5))
    //listBush.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Bush))
    private val listForest = Vector((0, 0), (10, 0), (1, 1), (9, 1), (2, 2), (8, 2), (3, 3), (7, 3), (3, 6),
      (7, 6), (2, 7), (8, 7), (1, 8), (9, 8), (0, 9), (10, 9))
    //listForest.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Forest))
    private val listRock = Vector((5, 0), (5, 1), (5, 2), (5, 7), (5, 8), (5, 9))
    //listStone.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Rock))
    private val listWater = Vector((1, 3), (1, 4), (1, 5), (1, 6), (9, 3), (9, 4), (9, 5), (9, 6))
    //listWater.foreach(j => mvector(j._1)(j._2).cobstacle = Option(new Water))

    override val mvector: Vector[Vector[Cell]]
    = Vector.tabulate(gridsX, gridsY)((i, j) => Cell(pos = (i, j),
      cobstacle = this.mvector(i)(j).cobstacle match {
        case _ if listBush.contains((i, j)) => Option(new Bush)
        case _ if listForest.contains((i, j)) => Option(new Forest)
        case _ if listRock.contains((i, j)) => Option(new Rock)
        case _ if listWater.contains((i, j)) => Option(new Water)
      }))
  }

  //  override def deepCopy: GameFieldInterface = {
  //    val gameFieldClone: Map2 = new Map2
  //    for (x <- 0 until gridsX) {
  //      for (y <- 0 until gridsY) {
  //        gameFieldClone.mvector(x)(y) = this.mvector(x)(y).deepClone()
  //      }
  //    }
  //    gameFieldClone
  //  }
}