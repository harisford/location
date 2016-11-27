package com.location.common

import akka.http.scaladsl.server.Route

/**
 * Created by michal on 26.11.16.
 */
trait RestRoutes {
  def routes: Route
}