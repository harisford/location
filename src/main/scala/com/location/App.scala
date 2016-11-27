package com.location

import akka.actor.ActorSystem
import com.google.inject.name.Names
import com.google.inject.{Key, Guice}
import com.location.common.RestServer

import scala.concurrent.ExecutionContext

/**
 * Created by michal on 05.11.16.
 */
class Boot extends App {

  val injector = Guice.createInjector(new MainModule)

  implicit val system: ActorSystem = injector.getInstance(classOf[ActorSystem])
  implicit val dbDispatcher: ExecutionContext = injector.getInstance(Key.get(classOf[ExecutionContext], Names.named("database")))

  injector.getInstance(classOf[RestServer]).start

}
