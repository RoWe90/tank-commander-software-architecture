package tankcommander


import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import tankcommander.controllerComponent.ControllerInterface
import tankcommander.controllerComponent.fileIoComponent.FileIOInterface
import tankcommander.controllerComponent.fileIoComponent.fileIoJsonImpl.FileIO

class TankCommanderModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[ControllerInterface].to[controllerComponent.controllerBaseImpl.Controller]
    bind[FileIOInterface].to[FileIO]
  }
}
