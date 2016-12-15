package org.dto.nextStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class NextStepSC (
    jsonId: Int = DTOIds.nextStep,
    dto: String = DTONames.nextStep,
    result: NextStepResultSC
)

object NextStepSC {
  implicit val format = Json.writes[NextStepSC]
}

//{"jsonId": 6, "method": "addNextStep", 
