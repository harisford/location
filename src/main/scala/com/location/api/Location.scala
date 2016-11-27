package com.location.api

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Directives._
import com.google.inject.Inject
import com.google.inject.name.Named
import com.location.common.RestRoutes
import com.location.domain.{Location, Point, Error}
import com.location.service.LocationService
import org.bson.types.ObjectId
import spray.json._
import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}
/**
 * Created by michal on 05.11.16.
 */

trait Protocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val pointFormat = jsonFormat2(Point.apply)
  implicit val locationFormat = jsonFormat4(Location.apply)
  implicit val errorFormat = jsonFormat2(Error.apply)

  implicit object ObjectIdFormat extends RootJsonFormat[ObjectId] {
    def read(json: JsValue): ObjectId = new ObjectId(json.convertTo[String])

    def write(obj: ObjectId): JsValue = JsString(obj.toString)
  }
}


class LocationResource @Inject()(service : LocationService) (implicit @Named("database") val db: ExecutionContext) extends Protocol with RestRoutes {
   val name = "locations"
   val getLocations = path(name) & get
   val addLocation = path(name) & post

   def addLocationRoute =
     addLocation {
        entity(as[Location]) {
          location => {
            onComplete(service.saveLocation(location)) {
               case Success(entity : Location) => complete(entity)
               case Failure(ex) => failWith(ex)
            }
         }
       }
     }

  def getLocationsRoute = {
    getLocations {
      parameters('typeName.as[String], 'ldx.as[Double], 'ldy.as[Double], 'rux.as[Double], 'ruy.as[Double]) {
        (typeName, ldx, ldy, rux, ruy) =>
              onComplete(service.getLocation(typeName, ldx, ldy, rux, ruy)) {
                case Success(list) => complete(list)
                case Failure(ex) => failWith(ex)
              }
            }
    }
  }

   val routes = addLocationRoute ~ getLocationsRoute
 }