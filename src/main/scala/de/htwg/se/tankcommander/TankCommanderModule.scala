package de.htwg.se.tankcommander

import com.google.inject.AbstractModule
import de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent._
import net.codingwell.scalaguice.ScalaModule

class TankCommanderModule extends AbstractModule with ScalaModule {
  override def configure(): Unit = {
    bind[FileIOInterface].to[fileIOXmlImpl.FileIO]
  }
}
