package org.dto.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 */

case class FirstStepResult (
    stepId: String,
    status: Boolean,
    message: String
)

object FirstStepResult {
  implicit val format = Json.writes[FirstStepResult] 
}