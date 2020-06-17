package tank.controller.baseImpl

import com.google.inject.{Guice, Inject, Injector}
import tank.TankModelModule
import tank.controller.TankModelControllerInterface
import tank.tankModelComponent.TankModel
import tank.tankModelComponent.daoComponent.DAOInterface

case class TankModelController @Inject() (var tankModel1: TankModel, var tankModel2: TankModel) extends TankModelControllerInterface {

  val injector: Injector = Guice.createInjector(new TankModelModule)
  val db : DAOInterface = injector.getInstance(classOf[DAOInterface])

  //TODO eventuell mit Options arbeiten

  override def initTank(whichTank: Int): Unit = {
    whichTank match {
      case 1 => tankModel1 = new TankModel()
      case 2 => tankModel2 = new TankModel()
      case _ =>
    }
  }

  override def getTankBaseDamage(whichTank: Int): Int = {
    whichTank match {
      case 1 => tankModel1.tankBaseDamage
      case 2 => tankModel2.tankBaseDamage
      case _ => 0
    }
  }

  override def getTankAccuracy(whichTank: Int): Int = {
    whichTank match {
      case 1 => tankModel1.accuracy
      case 2 => tankModel2.accuracy
      case _ => 0
    }
  }

  override def getTankHp(whichTank: Int): Int = {
    whichTank match {
      case 1 => tankModel1.hp
      case 2 => tankModel2.hp
      case _ => 0
    }
  }

  override def getTankPosC(whichTank: Int): (Int, Int) = {
    whichTank match {
      case 1 => tankModel1.posC
      case 2 => tankModel2.posC
      case _ => (0, 0)
    }
  }

  override def getTankFacing(whichTank: Int): String = {
    whichTank match {
      case 1 => tankModel1.facing
      case 2 => tankModel2.facing
      case _ => ""
    }
  }

  override def setTankBaseDamage(whichTank: Int, tankBaseDamage: Int): Unit = {
    whichTank match {
      case 1 => tankModel1 = tankModel1.copy(tankBaseDamage = tankBaseDamage)
      case 2 => tankModel2 = tankModel2.copy(tankBaseDamage = tankBaseDamage)
      case _ =>
    }
  }

  override def setTankAccuracy(whichTank: Int, accuracy: Int): Unit = {
    whichTank match {
      case 1 => tankModel1 = tankModel1.copy(accuracy = accuracy)
      case 2 => tankModel2 = tankModel2.copy(accuracy = accuracy)
      case _ => 0
    }
  }

  override def setTankHp(whichTank: Int, hp: Int): Unit = {
    whichTank match {
      case 1 => tankModel1 = tankModel1.copy(hp = hp)
      case 2 => tankModel2 = tankModel2.copy(hp = hp)
      case _ => 0
    }
  }

  override def setTankPosC(whichTank: Int, posC: (Int, Int)): Unit = {
    whichTank match {
      case 1 => tankModel1 = tankModel1.copy(posC = posC)
      case 2 => tankModel2 = tankModel2.copy(posC = posC)
      case _ => 0
    }
  }

  override def setTankFacing(whichTank: Int, facing: String): Unit = {
    whichTank match {
      case 1 => tankModel1 = tankModel1.copy(facing = facing)
      case 2 => tankModel2 = tankModel2.copy(facing = facing)
      case _ => 0
    }
  }

  override def save(): Unit = {
    db.saveTank("tankModel1", tankModel1.tankBaseDamage, tankModel1.accuracy, tankModel1.hp, tankModel1.posC, tankModel1.facing)
    db.saveTank("tankModel2", tankModel2.tankBaseDamage, tankModel2.accuracy, tankModel2.hp, tankModel2.posC, tankModel2.facing)
}

  override def load(): Unit = {
    val result0 = db.loadTank("tankModel1")
    tankModel1 = TankModel(result0._1, result0._2, result0._3, result0._4, result0._5)
    val result1 = db.loadTank("tankModel2")
    tankModel2 = TankModel(result1._1, result1._2, result1._3, result1._4, result1._5)
  }
}
