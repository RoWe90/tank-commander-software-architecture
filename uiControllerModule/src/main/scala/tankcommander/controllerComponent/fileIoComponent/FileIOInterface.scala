package tankcommander.controllerComponent.fileIoComponent

import tankcommander.controllerComponent.controllerBaseImpl.Controller

trait FileIOInterface {
  def save(): Unit

  def load(controller: Controller): Unit
}
