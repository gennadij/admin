/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.firstStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 *  {jsonId: 7, dto : FirstStep, params : {adminId : #40:0, kind  : first}}
 */

case class FirstStepCS (
    jsonId: Int = DTOIds.firstStep,
    dto: String = DTONames.firstStep,
    params: FirstStepParamsCS
)

object FirstStepCS {
  implicit val fortmat = Json.reads[FirstStepCS]
}