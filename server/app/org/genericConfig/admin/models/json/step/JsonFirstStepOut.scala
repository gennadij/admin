package org.genericConfig.admin.models.json.step

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonFirstStepOut (
    dtoId: Int = DTOIds.CREATE_FIRST_STEP,
    dto: String = DTONames.CREATE_FIRST_STEP,
    result: JsonFirstStepResult
)

object JsonFirstStepOut {
  implicit val format = Json.writes[JsonFirstStepOut]
}