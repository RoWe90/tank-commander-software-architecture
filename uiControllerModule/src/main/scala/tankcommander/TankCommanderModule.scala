import com.google.inject.AbstractModule
import controllerComponent.ControllerInterface
import controllerComponent.fileIoComponent.FileIOInterface
import controllerComponent.fileIoComponent.fileIoJsonImpl._

import net.codingwell.scalaguice.ScalaModule

class TankCommanderModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ControllerInterface].to[controllerComponent.controllerBaseImpl.Controller]
    bind[FileIOInterface].to[FileIO]
  }
}
