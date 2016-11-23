package org.dto.ServerClient.register

import play.api.libs.json.Json

case class ResultRegister (
    adminId: String,
    username: String
)

object ResultRegister {
  implicit val format = Json.reads[ResultRegister]
}