package com.regner.api

import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.HttpService
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder
import com.ibm.couchdb._

import scalaz.concurrent.Task



object Api extends ServerApp {
  case class Game(name: String)

  val typeMapping = TypeMapping(classOf[Game] -> "Game")

  val couch = CouchDb("192.168.99.100", 32772)
  val dbName = "games"
  val db = couch.db(dbName, typeMapping)

  val game1 = Game("game one")
  val game2 = Game("game two")

  couch.dbs.delete(dbName).ignoreError
  couch.dbs.create(dbName)
  db.docs.createMany(Seq(game1, game2))

  private val service = HttpService {
    case GET -> Root =>
      val results = db.docs.getMany.byTypeUsingTemporaryView[Game].build.query

      Ok("asd")

    case GET -> Root / IntVar(id) / "turns" =>
      Ok(List("List of turns for the given game...").asJson)
  }

  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080)
      .mountService(service, "/games/")
      .start
  }
}
