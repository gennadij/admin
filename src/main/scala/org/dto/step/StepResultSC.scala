/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import play.api.libs.json.Json

case class StepResultSC (
    stepId: String,
    adminId: String,
    kind: String
)

object StepResultSC {
  implicit val format = Json.writes[StepResultSC]
}