package org.genericConfig.admin.models.json.step

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonStepOut (
    dtoId: Int = DTOIds.CREATE_STEP,
    dto: String = DTONames.CREATE_STEP,
    result: JsonStepResult
)

object JsonStepOut {
  implicit val format = Json.writes[JsonStepOut]
}