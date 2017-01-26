package org.dto.connectionComponentToStep

import play.api.libs.json.Json

case class ConnectionComponentToStepResult (
    staus: Boolean,
    message: String
)

object ConnectionComponentToStepResult {
  implicit val format = Json.writes[ConnectionComponentToStepResult]
}