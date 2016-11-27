package org.dto

import play.api.libs.json.Json

case class LoginParamsCS (
    username: String,
    password: String
)

object LoginParamsCS {
  implicit val fortmat = Json.reads[LoginParamsCS]
}