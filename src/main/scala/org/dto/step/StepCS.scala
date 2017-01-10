/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 10, dto : Step, params : {adminId : #40:0, kind : default}
 */

case class StepCS (
    jsonId: Int = DTOIds.STEP,
    dto: String = DTONames.STEP,
    params: StepParamsCS
)


object StepCS {
  implicit val format = Json.reads[StepCS]
}

//{"jsonId": 6, "method": "addNextStep", "params": {"adminId": "AU#40:0", "kind": "default", "componentId": "#13:1"}