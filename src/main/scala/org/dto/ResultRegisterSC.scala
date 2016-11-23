package org.dto

import play.api.libs.json.Json

case class ResultRegisterSC (
    adminId: String,
    username: String
)

object ResultRegisterSC {
  implicit val format = Json.writes[ResultRegisterSC]
}