package com.location.common

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server._
import akka.stream.ActorMaterializer
import com.google.inject.Inject
import com.google.inject.name.Named
import com.location.api.CustomErrorHandling
import com.typesafe.scalalogging.LazyLogging
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by michal on 26.11.16.
 */
class RestServer @Inject()(@Named("ApiRouterActorRef") routes: Route)(implicit actorSystem: ActorSystem) extends
CustomErrorHandling with LazyLogging {

  def start = {
    val port = Option(System.getProperty("http.port")).map(_.toInt).getOrElse(8080)

    implicit val materializer = ActorMaterializer()

    val bindingFuture: Future[Http.ServerBinding] = Http().bindAndHandle(routes, "127.0.0.1", port)

    bindingFuture.andThen {
      case Success(succ) => logger.info(succ.toString)
      case Failure(ex) => logger.error(ex.toString)
    }
  }
}
