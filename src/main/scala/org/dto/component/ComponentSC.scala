package org.dto.component

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class ComponentSC (
    jsonId: Int = DTOIds.component,
    dto: String = DTONames.component,
    result: ComponentResultSC,
    message: String
)

object ComponentSC {
  implicit val format = Json.writes[ComponentSC]
}