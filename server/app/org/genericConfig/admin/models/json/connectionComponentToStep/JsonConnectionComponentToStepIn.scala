package org.genericConfig.admin.models.json.connectionComponentToStep

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */

/*
{
  "dtoId" : 9, 
  "dto" : "ConnectionComponentToStep", 
  "params" : {
    "componentId" : #40:1,
    "stepId" : "#40:2"
  }
}
 */

case class JsonConnectionComponentToStepIn (
    dtoId: Int = DTOIds.CONNECTION_COMPONENT_TO_STEP,
    dto: String = DTONames.CONNECTION_COMPONENT_TO_STEP,
    params: ConnectionComponentToStepParams
)

object JsonConnectionComponentToStepIn {
  implicit val format = Json.reads[JsonConnectionComponentToStepIn]
}