package player

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import player.playerComponent.daoComponent.DAOInterface
import player.playerComponent.daoComponent.mongoImpl.MongoImpl
import player.playerComponent.daoComponent.slickImpl.SlickImpl

class PlayerModelModule extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[DAOInterface].to[MongoImpl]
  }
}
