package com.regner.api.service

import com.regner.api.service.CirceImplicits._
import org.http4s.dsl._
import org.http4s.HttpService
import io.circe.generic.auto._
import io.circe.syntax._

object RootService {
  def apply(): HttpService = service

  private val service = HttpService {
    case GET -> Root =>
      Ok("Http4s API")

    case GET -> Root / "json" =>
      Ok(List(1, 2, 3, 4, 5, 6, 7).asJson)
  }
}
