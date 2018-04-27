package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonFirstStepOut (
    dto: String = JsonNames.CREATE_FIRST_STEP,
    result: JsonFirstStepResult
)

object JsonFirstStepOut {
  implicit val format = Json.writes[JsonFirstStepOut]
}