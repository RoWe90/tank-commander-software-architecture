package controller


class ControllerTest extends FlatSpec with Matchers {

  "A Controller" should "set up a Game when asked with properly initialised Objects" in {
    val controller = new Controller(new GameField)
    controller.setUpGame()

  }

}
