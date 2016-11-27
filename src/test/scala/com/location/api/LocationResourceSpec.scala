package com.location.api

import com.location.db.DatabaseSupport
import com.location.domain.{Point, Location, Error}
import akka.http.scaladsl.model.StatusCodes
import com.location.ApiTest
import com.location.service.LocationService

/**
 * Created by michal on 27.11.16.
 */
class LocationResourceSpec extends ApiTest {

  trait TestDatabaseSupport extends DatabaseSupport {
    override def collection() = testCollection()
  }


  val locationService = new LocationService with TestDatabaseSupport
  val routes = (new LocationResource(locationService)).routes

  describe("POST /locations") {
    it("should add new location") {
      val newLocation = Location("test", "point", Point(10.1, 20.2))
      postRequest(routes, "/locations", newLocation).~>(check {
        status should equal(StatusCodes.Created)
        responseAs[Location] shouldBe newLocation
      })
    }
  }

  describe("GET /locations") {
    it("should rejection request, get do not have query parameters") {
      getRequest(routes, "/locations").~>(check {
        status should equal(StatusCodes.BadRequest)
        responseAs[Error].desc should equal("Incorrect request")
      })
    }

    it("should return empty list") {
      getRequest(routes, "/locations?typeName=test&ldx=2.2&ldy=2.2&rux=4.2&ruy=4.2").~>(check {
        status should equal(StatusCodes.OK)
        responseAs[List[Location]].length should equal(0)
      })
    }

    it("should return location list") {
      val firstLocation = Location("test", "point", Point(10.1, 20.2))
      postRequest(routes, "/locations", firstLocation).~>(check {

        val secendLocation = Location("test", "point", Point(2.5, 2.5))
        postRequest(routes, "/locations", secendLocation).~>(check {

          getRequest(routes, "/locations?typeName=point&ldx=2.2&ldy=2.2&rux=4.2&ruy=4.2").~>(check {
            status should equal(StatusCodes.OK)
            responseAs[List[Location]].length should equal(1)
            responseAs[List[Location]].head shouldBe secendLocation
          })
        })
      })
    }
  }

}
