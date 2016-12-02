package org.dto.firstStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class FirstStepSC (
    jsonId: Int = DTOIds.firstStep,
    dto: String = DTONames.firstStep,
    message: String,
    params: FirstStepResultSC
)

object FirstStepSC {
  implicit val fortmat = Json.reads[FirstStepSC]
}