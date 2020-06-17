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

  override def savePlayer(name: String): Unit = {
    db.run(players += (0, name))
    db.run(players.result).map(_.foreach {
      case (id, name) =>
        println("  " + name)
    })
  }

  override def loadPlayer(which: Int): String = {
    val query = db.run(players.filter(_.id === which).result.headOption)
    val playerValues = Await.result(query, Duration.Inf).get
    playerValues._2
  }
}

case class Player(tag: Tag) extends Table[(Int, String)](tag, "player") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def * : ProvenShape[(Int, String)] = (id, name)

}
