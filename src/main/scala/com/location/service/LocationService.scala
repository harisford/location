package com.location.service

import com.google.inject.Inject
import com.location.db.DatabaseSupport
import com.location.domain.{Location, Point}
import com.google.inject.name.Named
import scala.concurrent.{ExecutionContext, Future}
import com.mongodb.casbah.Imports._

import com.mongodb.casbah.WriteConcern
import com.novus.salat._
import com.novus.salat.global._


/**
 * Created by michal on 05.11.16.
 */


class LocationService @Inject() (implicit @Named("database") val db: ExecutionContext) extends DatabaseSupport {

  def getLocation(typeName : String, ldx : Double, ldy : Double, rux : Double, ruy : Double) : Future[List[Location]] =
    findBoxLocations(typeName, Point(ldx, ldy), Point(rux, ruy))

  def findBoxLocations(typeName : String, leftDownCorner : Point, rightUpCorner : Point) : Future[List[Location]]= {
    val query = $and ( MongoDBObject("type"-> typeName),
      MongoDBObject(
        "$geoWithin" -> MongoDBObject(
          "$box" -> MongoDBList(
            MongoDBList(leftDownCorner.x, leftDownCorner.y),
            MongoDBList(rightUpCorner.x, rightUpCorner.y)
          )
        )
      ))

    Future {
      val results = collection().find(query)
      val locations = for (item <- results) yield grater[Location].asObject(item)
      locations.toList
    }
  }

  def saveLocation(location : Location) : Future[Location] = {
    Future {
      val dBObject = grater[Location].asDBObject(location)
      collection().save(dBObject, WriteConcern.Safe)
      grater[Location].asObject(dBObject)
    }
  }
}
