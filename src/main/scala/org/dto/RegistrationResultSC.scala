package org.dto

import play.api.libs.json.Json

case class RegistrationResultSC (
    adminId: String,
    username: String
)

object RegistrationResultSC {
  implicit val format = Json.writes[RegistrationResultSC]
}