package org.dto.ClientServer.authenticate

import play.api.libs.json.Json

case class ParamsAuthenticate (
    username: String,
    password: String
)

object ParamsAuthenticate {
  implicit val fortmat = Json.reads[ParamsAuthenticate]
}