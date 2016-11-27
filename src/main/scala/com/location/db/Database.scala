package com.location.db

import com.mongodb.casbah.{MongoClientURI, MongoClient}
import com.typesafe.config.ConfigFactory
import scala.concurrent.ExecutionContext.Implicits.global

object Database {
  val config = ConfigFactory.load().getConfig("database");
  private val DATABASE = config.getString("database_name")
  private val server = MongoClientURI(config.getString("url"))
  private val client = MongoClient(server)
  val database = client(DATABASE)
}

trait DatabaseSupport {
  def collection() = Database.database("locations")

}