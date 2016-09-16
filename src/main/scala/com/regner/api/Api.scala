package com.regner.api

import com.regner.api.service.RootService
import org.http4s.server.{Server, ServerApp}
import org.http4s.server.blaze.BlazeBuilder

import scalaz.concurrent.Task

object Api extends ServerApp {
  override def server(args: List[String]): Task[Server] = {
    BlazeBuilder.bindHttp(8080)
      .mountService(RootService(), "/")
      .start
  }
}
