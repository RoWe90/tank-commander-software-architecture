package tank.tankModelComponent.daoComponent.slickImpl

import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape
import tank.tankModelComponent.daoComponent.DAOInterface
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent._
import ExecutionContext.Implicits.global

class SlickImpl extends DAOInterface{

  private val tanks = TableQuery[Tank]

  val db = Database.forURL("jdbc:h2:mem:test1",
    driver ="org.h2.Driver",
    keepAliveConnection = true)
  db.run(DBIO.seq(tanks.schema.create))

  override def saveTank(name: String, tankBaseDamage: Int, accuracy: Int, hp: Int, posC: (Int, Int), facing: String): Unit = {
    db.run(tanks += (0, name, tankBaseDamage, accuracy, hp, posC._1, posC._2, facing))

    db.run(tanks.result).map(_.foreach {
      case (id, name, tankBaseDamage, accuracy, hp, posCx, posCy, facing) =>
        println("  " + name + tankBaseDamage + "\t" + accuracy + "\t" + hp + "\t" + posCx + "\t" + posCy + "\t" + facing)
    })

  }

  override def loadTank(which: String): (Int, Int, Int, (Int, Int), String) = {
    val query = db.run(tanks.filter(_.name === which).sortBy(_.id.desc).result.headOption)
    val tankValues = Await.result(query, Duration.Inf).get
    (tankValues._3, tankValues._4, tankValues._5, (tankValues._6, tankValues._7), tankValues._8)
  }
}

case class Tank(tag: Tag) extends Table[(Int, String, Int, Int, Int, Int, Int, String)](tag, "tank") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")
  def tankBaseDamage: Rep[Int] = column[Int]("tankBaseDamage")
  def accuracy: Rep[Int] = column[Int]("accuracy")
  def hp: Rep[Int] = column[Int]("hp")
  def posCx: Rep[Int] = column[Int]("posC_x")
  def posCy: Rep[Int] = column[Int]("posC_y")
  def facing: Rep[String] = column[String]("facing")

  def * : ProvenShape[(Int, String, Int, Int, Int, Int, Int, String)] = (id, name, tankBaseDamage, accuracy, hp, posCx, posCy, facing)
}