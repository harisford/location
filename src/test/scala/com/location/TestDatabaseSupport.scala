package com.location

import akka.http.scaladsl.marshalling._
import akka.http.scaladsl.server._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.location.api.CustomErrorHandling
import com.location.db.DatabaseSupport
import com.mongodb.casbah.MongoClient
import org.scalatest.matchers.ShouldMatchers
import com.github.simplyscala.{MongoEmbedDatabase, MongodProps}
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{BeforeAndAfter, BeforeAndAfterAll, _}
import com.location.api.Protocol
import scala.concurrent.ExecutionContext

/**
 * Created by michal on 26.11.16.
 */

trait ApiTest extends FunSpec with DatabaseSupport with ShouldMatchers with ScalaFutures with BeforeAndAfterAll
with BeforeAndAfter with ScalatestRouteTest with CustomErrorHandling with Protocol with MongoEmbedDatabase {

  private var maybeProps: Option[MongodProps] = None

  private val MONGO_PORT = 12345
  private val MONGO_HOST = "127.0.0.1"
  private val MONGO_DATABASE = "test"
  private val MONGO_COLLECTION = "locations"


  def testCollection() = {
    val connection = MongoClient(MONGO_HOST, MONGO_PORT)
    connection(MONGO_DATABASE)(MONGO_COLLECTION)
  }

  override protected def beforeAll(): Unit = {
    maybeProps = Some(mongoStart())
  }


  override protected def afterAll(): Unit = {
    maybeProps.foreach(mongoStop)
  }

  def postRequest[T](routes: Route, uri: String, content: T)(implicit m: ToEntityMarshaller[T], ec: ExecutionContext) = {
    val request = Post(uri, content)
    request.~>(routes)(TildeArrow.injectIntoRoute)
  }

  def getRequest(routes: Route, uri: String) = {
    val request = Get(uri)
    request.~>(routes)(TildeArrow.injectIntoRoute)
  }
}
