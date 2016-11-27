package com.location.core

import akka.actor.{ActorRefFactory, ActorSystem}
import com.google.inject.AbstractModule
import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Provider
import com.google.inject.Provides
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.location.core.AkkaModule.ActorSystemProvider
import net.codingwell.scalaguice.ScalaModule
import scala.concurrent.ExecutionContext

/**
 * Created by michal on 26.11.16.
 */
object AkkaModule {
  class ActorSystemProvider @Inject()(val injector: Injector) extends Provider[ActorSystem] {
    override def get() = {
      val system = ActorSystem("RestLocation")
      // add the GuiceAkkaExtension to the system, and initialize it with the Guice injector
      GuiceAkkaExtension(system).initialize(injector)
      system
    }
  }
}

/**
 * A module providing an Akka ActorSystem.
 */
class AkkaModule extends AbstractModule with ScalaModule {

  override def configure() {
    bind[ActorSystem].toProvider[ActorSystemProvider].asEagerSingleton()
  }

  /**
   * Provides a singleton factory to be injected whenever an ActorRefFactory
   * is required.
   */
  @Provides @Singleton
  def provideActorRefFactory(system: ActorSystem): ActorRefFactory = {
    system
  }

  /**
   * Provides a singleton execution context to be injected whenever an ExecutionContext
   * is required.
   */
  @Provides @Singleton
  def provideExecutionContext(system: ActorSystem): ExecutionContext = {
    system.dispatcher
  }

  @Provides @Singleton @Named("database")
  def provideDBExecutionContext(system: ActorSystem): ExecutionContext = {
    system.dispatchers.lookup("contexts.db-operations")
  }
}

