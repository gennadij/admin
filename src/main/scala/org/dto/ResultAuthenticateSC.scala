package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
case class ResultAuthenticateSC (
                          adminId: String,
                          username: String,
                          authentication: Boolean
                         )

object ResultAuthenticateSC{
  implicit val format = Json.writes[ResultAuthenticateSC]
}
