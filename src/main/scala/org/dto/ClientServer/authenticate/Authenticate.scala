package org.dto.ClientServer.authenticate

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class Authenticate (
    jsonId: Int = 2,
    dto: String = "autheticate",
    params: ParamsAuthenticate
)

object Authenticate {
  implicit val fortmat = Json.reads[Authenticate]
}