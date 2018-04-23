package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonStepOut (
    json: String = JsonNames.CREATE_STEP,
    result: JsonStepResult
)

object JsonStepOut {
  implicit val format = Json.writes[JsonStepOut]
}