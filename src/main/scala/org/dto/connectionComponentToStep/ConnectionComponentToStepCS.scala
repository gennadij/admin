package org.dto.connectionComponentToStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */


case class ConnectionComponentToStepCS (
    dtoId: Int = DTOIds.CONNECTION_COMPONENT_TO_STEP,
    dto: String = DTONames.CONNECTION_COMPONENT_TO_STEP,
    params: ConnectionComponentToStepParams
)

object ConnectionComponentToStepCS {
  implicit val format = Json.reads[ConnectionComponentToStepCS]
}