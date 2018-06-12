package de.htwg.se.tankcommander.obsolete

//TODO Define a class containing all needed values and methods for a Inventory-object
class Inventory() {
  var Items = new Array[Item](3)

  def addItemToInventory(itemToAdd: Item): Unit = {
    if (checkIfNotFull()) {
      Items(Items.length + 1) = itemToAdd;
    }else{
      print("Inventory is already full")
    }
  }

  //checks if Inventory is full
  def checkIfNotFull(): Boolean = {
    if (Items.length < 3) {
      return true
    }
    false
  }

  def containsItem(item: String): Boolean = {
    for (i <- 0 until Items.length) {
      if (Items(i).toString == item) true
    }
    false
  }

}


