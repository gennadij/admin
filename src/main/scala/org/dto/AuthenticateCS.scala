package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class AuthenticateCS (
    jsonId: Int = 2,
    dto: String = "autheticate",
    params: ParamsAuthenticateCS
)

object AuthenticateCS {
  implicit val fortmat = Json.reads[AuthenticateCS]
}