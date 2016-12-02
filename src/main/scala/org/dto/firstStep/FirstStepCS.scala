package org.dto.firstStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class FirstStepCS (
    jsonId: Int = DTOIds.firstStep,
    dto: String = DTONames.firstStep,
    params: FirstStepParamsCS
)

object FirstStepCS {
  implicit val fortmat = Json.reads[FirstStepCS]
}