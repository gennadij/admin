/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 1.1.2017
 */
case class StepSC (
    dtoId: Int = DTOIds.CREATE_STEP,
    dto: String = DTONames.CREATE_STEP,
    result: StepResult
)

object StepSC {
  implicit val format = Json.writes[StepSC]
}

//{"jsonId": 6, "method": "addNextStep", 
