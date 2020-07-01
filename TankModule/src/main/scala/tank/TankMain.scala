package tank

import tank.controller.{TankModelControllerInterface, baseImpl}
import tank.controller.baseImpl.TankModelController
import tank.http.HttpServer
import tank.tankModelComponent.TankModel

object TankMain {
  val controller: TankModelControllerInterface = baseImpl.TankModelController(TankModel(), TankModel())
  val webserver: HttpServer = new HttpServer(controller)

  def main(args: Array[String]): Unit = {
    println("TankModule started...")
  }

}
