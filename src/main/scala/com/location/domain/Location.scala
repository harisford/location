package com.location.domain

import org.bson.types.ObjectId

/**
 * Created by michal on 05.11.16.
 */

case class Point(x : Double, y : Double)

case class Location(name : String, typeName : String, coordinates : Point, _id: ObjectId = null)

case class Error(desc : String, reason : String)
