import controller.TankModelControllerInterface
import controller.baseImpl.TankModelController
import http.HttpServer
import tankModelComponent.TankModel

object TankMain {
  val controller: TankModelControllerInterface = TankModelController(TankModel(), TankModel())
  val webserver: HttpServer = new HttpServer(controller)

  def main(args: Array[String]): Unit = {
    println("TankModule started...")
  }

}
