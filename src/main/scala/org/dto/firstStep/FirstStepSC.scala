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
 * {jsonId : 7, dto : FirstStep, result : {stepId : #12:1, status : true, message : Nachricht}} 
 */

case class FirstStepSC (
    jsonId: Int = DTOIds.firstStep,
    dto: String = DTONames.firstStep,
    result: FirstStepResultSC
)

object FirstStepSC {
  implicit val fortmat = Json.writes[FirstStepSC]
}