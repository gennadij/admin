package org.genericConfig.admin.models.json.connectionComponentToStep

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */

case class JsonConnectionComponentToStepOut (
    json: String = JsonNames.CONNECTION_COMPONENT_TO_STEP,
    result: ConnectionComponentToStepResult
)

object JsonConnectionComponentToStepOut {
  implicit val format = Json.writes[JsonConnectionComponentToStepOut]
}