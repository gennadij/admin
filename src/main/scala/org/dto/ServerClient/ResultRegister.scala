package org.dto.ServerClient

import play.api.libs.json.Json

case class ResultRegister (
    adminId: String,
    username: String
)

object ResultRegister {
  implicit val format = Json.reads[ResultRegister]
}