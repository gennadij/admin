package org.dto.connectionComponentToStep

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */

case class ConnectionComponentToStepParams (
    componentId: String,
    stepId: String
)

object ConnectionComponentToStepParams {
  implicit val format = Json.reads[ConnectionComponentToStepParams]
}