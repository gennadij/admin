package org.dto.connectionComponentToStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class ConnectionComponentToStepCS (
    dtoId: Int = DTOIds.CONNECTION_COMPONENT_TO_STEP,
    dto: String = DTONames.CONNECTION_COMPONENT_TO_STEP,
    params: ConnectionComponentToStepParams
)

object ConnectionComponentToStepCS {
  implicit val format = Json.reads[ConnectionComponentToStepCS]
}