package org.dto.ServerClient.authenticate

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
class ResultAuthenticate (
                          adminId: String,
                          username: String,
                          authentication: Boolean
                         )

object ResultAuthenticate{
  implicit val format = Json.format[ResultAuthenticate]
}
