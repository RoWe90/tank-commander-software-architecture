package tank.tankModelComponent.slickComponent.slickImpl

import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape
import tank.tankModelComponent.slickComponent.SlickInterface
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SlickImpl extends SlickInterface{

  private val tanks = TableQuery[Tank]

  val db = Database.forURL("jdbc:h2:mem:test1",
    driver ="org.h2.Driver",
    keepAliveConnection = true)
  db.run(DBIO.seq(tanks.schema.create))

  override def saveTank(tankBaseDamage: Int, accuracy: Int, hp: Int, posC: (Int, Int), facing: String): Unit = {
    db.run(tanks += (0, tankBaseDamage, accuracy, hp, posC._1, posC._2, facing))
  }

  override def loadTank(which: Int): (Int, Int, Int, (Int, Int), String) = {
    val query = db.run(tanks.filter(_.id === which).result.headOption)
    val tankValues = Await.result(query, Duration.Inf).get
    (tankValues._2, tankValues._3, tankValues._4, (tankValues._5, tankValues._6), tankValues._7)
    //TODO: gibt immer nur den obersten tank aus nicht beide
  }
}

case class Tank(tag: Tag) extends Table[(Int, Int, Int, Int, Int, Int, String)](tag, "tank") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def tankBaseDamage: Rep[Int] = column[Int]("tankBaseDamage")
  def accuracy: Rep[Int] = column[Int]("accuracy")
  def hp: Rep[Int] = column[Int]("hp")
  def posCx: Rep[Int] = column[Int]("posC_x")
  def posCy: Rep[Int] = column[Int]("posC_y")
  def facing: Rep[String] = column[String]("facing")

  def * : ProvenShape[(Int, Int, Int, Int, Int, Int, String)] = (id, tankBaseDamage, accuracy, hp, posCx, posCy, facing)
}