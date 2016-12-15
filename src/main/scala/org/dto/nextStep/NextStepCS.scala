package org.dto.nextStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class NextStepCS (
    jsonId: Int = DTOIds.nextStep,
    dto: String = DTONames.nextStep,
    params: NextStepParamsCS
)


object NextStepCS {
  implicit val format = Json.reads[NextStepCS]
}

//{"jsonId": 6, "method": "addNextStep", "params": {"adminId": "AU#40:0", "kind": "default", "componentId": "#13:1"}