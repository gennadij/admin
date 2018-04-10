package org.genericConfig.admin.models.json.connectionComponentToStep

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */

/*
{
  "dtoId : 9, 
  "dto" : "ConnectionComponentToStep", 
  "result" : {
    "status" : "SECCESSFUL_CONNECTION_COMPONENT_TO_STEP", 
    "message" : "Component mit dem Step wurde Verbunden"
  }
}


 */
case class JsonConnectionComponentToStepOut (
    dtoId: Int = DTOIds.CONNECTION_COMPONENT_TO_STEP,
    dto: String = DTONames.CONNECTION_COMPONENT_TO_STEP,
    result: ConnectionComponentToStepResult
)

object JsonConnectionComponentToStepOut {
  implicit val format = Json.writes[JsonConnectionComponentToStepOut]
}