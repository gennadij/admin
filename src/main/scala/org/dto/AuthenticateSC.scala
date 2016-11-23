package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
case class AuthenticateSC (
                   jsonId: Int = 2,
                   dto: String = "Autheticate",
                   result: ResultAuthenticateSC
                   )

object AuthenticateSC {
  implicit val format = Json.writes[AuthenticateSC]
}
