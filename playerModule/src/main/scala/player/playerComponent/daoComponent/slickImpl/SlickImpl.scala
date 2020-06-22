package player.playerComponent.daoComponent.slickImpl

import player.playerComponent.daoComponent.DAOInterface
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape
import scala.concurrent.Await
import scala.concurrent.duration.Duration

import scala.concurrent._
import ExecutionContext.Implicits.global

class SlickImpl extends DAOInterface{

  private val players = TableQuery[Player]

  val db = Database.forURL("jdbc:h2:mem:test1",
    driver ="org.h2.Driver",
    keepAliveConnection = true)
  db.run(DBIO.seq(players.schema.create))

  override def savePlayer(which: Int, name: String): Unit = {
    db.run(players += (0, which, name))
    db.run(players.result).map(_.foreach {
      case (id, which, name) =>
        println("  " + which + name)
    })
  }

  override def loadPlayer(which: Int): String = {
    val query = db.run(players.filter(_.which === which).sortBy(_.id.desc).result.headOption)
    val playerValues = Await.result(query, Duration.Inf).get
    playerValues._3
  }
}

case class Player(tag: Tag) extends Table[(Int, Int, String)](tag, "player") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def which: Rep[Int] = column[Int]("which")
  def name: Rep[String] = column[String]("name")

  def * : ProvenShape[(Int, Int, String)] = (id, which, name)

}
