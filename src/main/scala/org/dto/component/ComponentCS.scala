package org.dto.component

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames

case class ComponentCS (
    jsonId: Int = DTOIds.component,
    dto: String = DTONames.component,
    params: ComponentParamsCS
)

object ComponentCS {
  implicit val format = Json.reads[ComponentCS]
}