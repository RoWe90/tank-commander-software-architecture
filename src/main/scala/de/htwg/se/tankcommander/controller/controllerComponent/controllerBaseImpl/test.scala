import scala.collection.mutable._
import net.liftweb.json._
import net.liftweb.json.Serialization.write

object LiftJsonTest extends App {
  // create a JSON string from the Person, then print it
  implicit val formats = DefaultFormats
  val jsonString = write(p)
  println(jsonString)
}