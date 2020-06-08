package model

import playerComponent.Player

import scala.util.Try

trait FileIOInterface {
  def save(player: Player, path: String): Try[Player]
  def load(player: Player, path: String): Try[Unit]
}
