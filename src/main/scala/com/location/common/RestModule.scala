package com.location.common

import akka.actor.{ActorRefFactory, ActorSystem}
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.directives.DebuggingDirectives
import com.google.inject.name.Named
import com.google.inject.{Singleton, Provides}
import net.codingwell.scalaguice.ScalaModule

/**
 * Created by michal on 26.11.16.
 */
class RestModule  extends ScalaModule {

  override def configure(): Unit = {}

  @Provides
  @Singleton
  @Named("ApiRouterActorRef")
  def provideApiRoute(system: ActorSystem, actorRefFactory: ActorRefFactory, apiSet: Set[RestRoutes]): Route = {
    implicit def actorRefFactoryImp = actorRefFactory

    val apis = apiSet.toSeq
    val routes = apis.tail.foldLeft(apis.head.routes) { (chain, next) =>
      chain ~ next.routes
    }

    DebuggingDirectives.logRequest(" Request Client Rest", Logging.InfoLevel)(routes)
    DebuggingDirectives.logResult(" Result Client Rest", Logging.InfoLevel)(routes)
    routes
  }


}
