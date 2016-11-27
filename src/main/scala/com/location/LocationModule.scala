package com.location

import com.location.api.LocationResource
import com.location.common.ApisModule
import com.location.service.LocationService
import com.google.inject.Singleton

/**
 * Created by michal on 26.11.16.
 */
class LocationModule extends ApisModule {
  override def configure(): Unit = {
    bind[LocationService].in[Singleton]
    bind[LocationResource].in[Singleton]
  }
}
