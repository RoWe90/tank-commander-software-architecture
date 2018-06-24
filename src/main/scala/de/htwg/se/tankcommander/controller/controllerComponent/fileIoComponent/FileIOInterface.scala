package de.htwg.se.tankcommander.controller.controllerComponent.fileIoComponent

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller

trait FileIOInterface {
  def save(): Unit

  def load(controller: Controller): Unit
}
