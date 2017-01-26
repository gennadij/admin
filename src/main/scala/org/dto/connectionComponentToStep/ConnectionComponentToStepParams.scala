package org.dto.connectionComponentToStep

import play.api.libs.json.Json

case class ConnectionComponentToStepParams (
    componentId: String,
    stepId: String
)

object ConnectionComponentToStepParams {
  implicit val format = Json.reads[ConnectionComponentToStepParams]
}