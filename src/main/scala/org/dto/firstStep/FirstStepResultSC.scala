/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.firstStep

import play.api.libs.json.Json

case class FirstStepResultSC (
  id: String,
  stepId: String,
  adminId: String,
  kind: String
  
)

object FirstStepResultSC {
  implicit val fortmat = Json.writes[FirstStepResultSC]
}