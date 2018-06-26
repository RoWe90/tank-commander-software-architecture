package controller

import de.htwg.se.tankcommander.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.tankcommander.model.gridComponent.gridBaseImpl.GameField
import org.scalatest.{FlatSpec, Matchers}


class ControllerTest extends FlatSpec with Matchers {

  "A Controller" should "set up a Game when asked with properly initialised Objects" in {
    val controller = new Controller(new GameField)
    controller.setUpGame()

  }

}
