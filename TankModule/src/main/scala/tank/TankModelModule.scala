package tank

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import tank.tankModelComponent.daoComponent.DAOInterface
import tank.tankModelComponent.daoComponent.slickImpl.SlickImpl

class TankModelModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[DAOInterface].to[SlickImpl]
  }
}
