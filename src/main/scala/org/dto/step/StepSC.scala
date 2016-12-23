/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

case class StepSC (
    jsonId: Int = DTOIds.step,
    dto: String = DTONames.step,
    result: StepResultSC
)

object StepSC {
  implicit val format = Json.writes[StepSC]
}

//{"jsonId": 6, "method": "addNextStep", 