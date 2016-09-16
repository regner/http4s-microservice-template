package com.regner.api

import org.http4s.headers.`Content-Type`
import io.circe.{Json => CirceJson}
import org.http4s.{Charset, EntityEncoder, MediaType}

package object service {
  object CirceImplicits {
    implicit val circeJsonEncoder: EntityEncoder[CirceJson] =
      EntityEncoder
        .stringEncoder(Charset.`UTF-8`)
        .contramap { json: CirceJson => json.noSpaces }
        .withContentType(`Content-Type`(MediaType.`application/json`, Charset.`UTF-8`))
  }
}
