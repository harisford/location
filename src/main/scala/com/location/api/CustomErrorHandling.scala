package com.location.api

import com.location.domain.Error
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server._

/**
 * Created by michal on 26.11.16.
 */
trait CustomErrorHandling extends Protocol {

  implicit def exceptionHandler: ExceptionHandler = ExceptionHandler {
    case exception =>
      exception match {
        case e: java.util.NoSuchElementException => ctx =>
          ctx.complete(StatusCodes.NotFound)
        case e: IllegalArgumentException => ctx =>
          ctx.complete(StatusCodes.BadRequest, e.getMessage())
        case e: Exception => ctx =>
          ctx.complete(StatusCodes.InternalServerError, e.getMessage)

      }
  }

  implicit def rejectionHandler =
    RejectionHandler.newBuilder()
    .handle {
      case MalformedRequestContentRejection(msg, cause) =>
        complete(StatusCodes.BadRequest, Error("Incorrect request", cause.get.getMessage))
    }
    .handle {
      case UnsupportedRequestContentTypeRejection(msg) =>
        complete(StatusCodes.BadRequest, Error("Incorrect request", msg.toString()))
    }
    .handleAll[MethodRejection] { methodRejections =>
      val names = methodRejections.map(_.supported.name)
      complete((StatusCodes.MethodNotAllowed, s"Can't do that! Supported: ${names mkString " or "}!"))
    }
    .handleNotFound {
      complete((StatusCodes.NotFound, "Not here!"))
    }
    .result()

}
