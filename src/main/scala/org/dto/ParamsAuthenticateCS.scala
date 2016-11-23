package org.dto

import play.api.libs.json.Json

case class ParamsAuthenticateCS (
    username: String,
    password: String
)

object ParamsAuthenticateCS {
  implicit val fortmat = Json.reads[ParamsAuthenticateCS]
}