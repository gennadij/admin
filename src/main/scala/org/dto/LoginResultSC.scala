package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
case class LoginResultSC (
                          adminId: String,
                          username: String,
                          status: Boolean,
                          message: String
                         )

object LoginResultSC{
  implicit val format = Json.writes[LoginResultSC]
}
