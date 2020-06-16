package player

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import player.playerComponent.slickComponent.SlickInterface
import player.playerComponent.slickComponent.slickImpl.SlickImpl

class PlayerModelModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[SlickInterface].to[SlickImpl]
  }
}
