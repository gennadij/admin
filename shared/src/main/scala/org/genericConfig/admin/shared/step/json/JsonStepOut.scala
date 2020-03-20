package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonStepOut (
    json: String,
    result: JsonStepResult
)

object JsonStepOut {
  implicit val format = Json.format[JsonStepOut]
}