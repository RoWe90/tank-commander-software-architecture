package player.playerComponent.slickComponent.slickImpl

import player.playerComponent.slickComponent.SlickInterface
import slick.jdbc.H2Profile.api._
import slick.lifted.ProvenShape
import scala.concurrent.Await
import scala.concurrent.duration.Duration

class SlickImpl extends SlickInterface{

  private val players = TableQuery[Player]

  val db = Database.forURL("jdbc:h2:mem:test1",
    driver ="org.h2.Driver",
    keepAliveConnection = true)
  db.run(DBIO.seq(players.schema.create))

  override def savePlayer(name: String): Unit = {
    db.run(players += (0, name))
  }

  override def loadPlayer(which: Int): String = {
    val query = db.run(players.filter(_.id === which).result.headOption)
    val playerValues = Await.result(query, Duration.Inf).get
    playerValues._2
    //TODO: gibt immer nur den obersten player aus nicht beide
  }
}

case class Player(tag: Tag) extends Table[(Int, String)](tag, "player") {

  def id: Rep[Int] = column[Int]("ID", O.PrimaryKey, O.AutoInc)

  def name: Rep[String] = column[String]("name")

  def * : ProvenShape[(Int, String)] = (id, name)

}
