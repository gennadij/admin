package org.dto.step

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 *  Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class StepCS (
    dtoId: Int = DTOIds.CREATE_STEP,
    dto: String = DTONames.CREATE_STEP,
    params: StepParams
)


object StepCS {
  implicit val format = Json.reads[StepCS]
}

//{"jsonId": 6, "method": "addNextStep", "params": {"adminId": "AU#40:0", "kind": "default", "componentId": "#13:1"}