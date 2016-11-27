package com.location

import com.location.common.RestModule
import com.location.core.AkkaModule
import net.codingwell.scalaguice.ScalaModule

/**
 * Created by michal on 26.11.16.
 */
class MainModule extends ScalaModule {
  override def configure(): Unit = {
    install(new AkkaModule)
    install(new RestModule)
    install(new LocationModule)
  }

}
