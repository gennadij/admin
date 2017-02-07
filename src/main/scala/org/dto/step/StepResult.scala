/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class StepResult (
    stepId: String,
    status: Boolean,
    message: String
)

object StepResult {
  implicit val format = Json.writes[StepResult]
}