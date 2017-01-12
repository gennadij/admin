package org.dto.step

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 * 
 * jsonId: 7, dto : FirstStep, params ...
 */

case class FirstStepCS (
    dtoId: Int = DTOIds.FIRST_STEP,
    dto: String = DTONames.FIRST_STEP,
    params: FirstStepParams
    
)

object FirstStepCS {
  implicit val format = Json.reads[FirstStepCS]
}