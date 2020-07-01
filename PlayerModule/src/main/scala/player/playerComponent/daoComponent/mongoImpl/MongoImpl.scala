package player.playerComponent.daoComponent.mongoImpl

import player.playerComponent.daoComponent.DAOInterface
import java.lang.Math.toIntExact
import java.time.Instant

import com.mongodb.BasicDBObject
import org.mongodb.scala.bson.codecs.Macros._
import org.mongodb.scala.{MongoClient, MongoCollection, MongoDatabase}
import org.mongodb.scala.model.Filters._
import org.bson.codecs.configuration.CodecRegistries._

import scala.concurrent.duration._
import org.mongodb.scala.bson.codecs.DEFAULT_CODEC_REGISTRY

import scala.concurrent.Await

class MongoImpl extends DAOInterface {

  private val uri = "mongodb://127.0.0.1:27017/?&gssapiServiceName=mongodb"
  private val client: MongoClient = MongoClient(uri)

  private val customCodecs = fromProviders(classOf[Player])
  private val codecRegistry = fromRegistries(customCodecs, DEFAULT_CODEC_REGISTRY)
  private val db: MongoDatabase = client.getDatabase("myDB").withCodecRegistry(codecRegistry)

  val players: MongoCollection[Player] = db.getCollection("Players")

  override def savePlayer(which: Int, name: String): Unit = {
    val time: Long = Instant.now.getEpochSecond
    val bar: Int = toIntExact(time)
    Await.result(players.insertOne(Player(bar, which, name)).toFuture(), 5 seconds)
  }

  override def loadPlayer(which: Int): String = {
    val dbplayer = Await.result(players.find(equal("which", which)).sort(new BasicDBObject("_id", -1)).first().toFuture(), 5 seconds)
    dbplayer.name
  }
}

case class Player(id: Int, which: Int, name: String)