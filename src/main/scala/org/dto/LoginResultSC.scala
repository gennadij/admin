package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
case class LoginResultSC (
                          adminId: String,
                          username: String,
                          authentication: Boolean
                         )

object LoginResultSC{
  implicit val format = Json.writes[LoginResultSC]
}
