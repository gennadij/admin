package org.dto.step

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 * 
 * jsonId : 7, dto : FirstStep, result ...
 */

case class FirstStepSC (
    dtoId: Int = DTOIds.FIRST_STEP,
    dto: String = DTONames.CREATE_FIRST_STEP,
    result: FirstStepResult
)

object FirstStepSC {
  implicit val format = Json.writes[FirstStepSC]
}