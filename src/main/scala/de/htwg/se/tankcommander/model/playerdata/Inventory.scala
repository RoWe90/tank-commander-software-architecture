package de.htwg.se.tankcommander.model.playerdata

import de.htwg.se.tankcommander.model.items.Item

//TODO Define a class containing all needed values and methods for a Inventory-object
class Inventory() {
  private var Items = new Array[Item](3)

  def addItemToInventory(itemToAdd: Item): Unit = {
    if (checkIfNotFull()) {
      Items(Items.length + 1) = itemToAdd;
    }
  }

  //checks if Inventory is full
  def checkIfNotFull(): Boolean = {
    if (Items.length < 3) {
      return true
    }
    false
  }
}


