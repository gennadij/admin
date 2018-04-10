package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 */

case class JsonFirstStepResult (
    stepId: String,
    status: String,
    message:  String
)

object JsonFirstStepResult {
  implicit val format = Json.writes[JsonFirstStepResult] 
}