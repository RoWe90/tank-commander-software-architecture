package tank.tankModelComponent.daoComponent.mongoImpl

import org.mongodb.scala.MongoClient
import tank.tankModelComponent.daoComponent.DAOInterface
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


class MongoImpl extends DAOInterface{

  private val uri = "mongodb://127.0.0.1:27017/?&gssapiServiceName=mongodb"
  private val client: MongoClient = MongoClient(uri)

  private val customCodecs = fromProviders(classOf[Tank])
  private val codecRegistry = fromRegistries(customCodecs, DEFAULT_CODEC_REGISTRY)
  private val db: MongoDatabase = client.getDatabase("myDB").withCodecRegistry(codecRegistry)

  val tanks: MongoCollection[Tank] = db.getCollection("Tanks")

  override def saveTank(name: String, tankBaseDamage: Int, accuracy: Int, hp: Int, posC: (Int, Int), facing: String): Unit = {
    val time: Long = Instant.now.getEpochSecond
    val bar: Int = toIntExact(time)
    Await.result(tanks.insertOne(Tank(bar, name, tankBaseDamage, accuracy, hp, posC._1, posC._2, facing)).toFuture(), 5 seconds)
  }

  override def loadTank(which: String): (Int, Int, Int, (Int, Int), String) = {
    val dbtanks = Await.result(tanks.find(equal("name", which)).sort(new BasicDBObject("_id", -1)).first().toFuture(), 5 seconds)
    (dbtanks.tankBaseDamage, dbtanks.accuracy, dbtanks.hp, (dbtanks.posCx, dbtanks.posCy), dbtanks.facing)
  }
}

case class Tank(id: Int, name: String, tankBaseDamage: Int, accuracy: Int, hp: Int, posCx: Int, posCy: Int, facing: String)