package de.htwg.se.tankcommander.model

object Commands extends Enumeration {
  type Commands = Value
  val START, UP, DOWN, RIGHT, LEFT, EXIT, SHOOT, END_TURN, UNDO, REDO = Value

  val map: Map[Commands, String] = Map[Commands, String](
    START -> "start",
    UP ->    "up",
    DOWN ->  "down",
    RIGHT -> "right",
    LEFT ->  "left",
    EXIT ->  "exit",
    SHOOT -> "shoot",
    END_TURN -> "end turn",
    UNDO ->     "undo",
    REDO ->     "redo")

  def isOrderType(s: String): Boolean = values.exists(_.toString == s)
}
