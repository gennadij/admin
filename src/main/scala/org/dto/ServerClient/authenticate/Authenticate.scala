package org.dto.ServerClient.authenticate

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
class Authenticate (
                   jsonId: Int = 2,
                   dto: String = "Autheticate",
                   result: ResultAuthenticate
                   )

object Authenticate {
  implicit val format = Json.format[Authenticate]
}
