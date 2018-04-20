package de.htwg.se.tankcommander.model


//TODO Define a class containing all needed values and methods for a tank-object
class TankModel(name: String) {
  private val tankName = name
  private val tankBaseDamage = 10
  private val accuracy = 100;
  private var healthpoints = 100
  private var armor = 0;
  private var shield = 0;
  var position: Cell = null
  private var facing = null

  def shoot(): Unit = {

  }

  def move(): Unit = {

  }

  def useItem(): Unit = {

  }
}

