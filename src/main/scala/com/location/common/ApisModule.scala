package com.location.common

import net.codingwell.scalaguice.{ScalaMultibinder, ScalaModule}

/**
 * Created by michal on 26.11.16.
 */
trait ApisModule extends ScalaModule {

  protected[this] def bindApi = {
    ScalaMultibinder.newSetBinder[RestRoutes](binderAccess).addBinding
  }

}
