package tank

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import tank.tankModelComponent.slickComponent.SlickInterface
import tank.tankModelComponent.slickComponent.slickImpl.SlickImpl

class TankModelModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[SlickInterface].to[SlickImpl]
  }
}
