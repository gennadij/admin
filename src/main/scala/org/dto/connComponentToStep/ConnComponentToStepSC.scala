/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.connComponentToStep

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 20.12.2016
 * 
 * {jsonId : 11, dto : ConnComponentToStep, result : ...}
 */
case class ConnComponentToStepSC (
    jsonId: Int = DTOIds.connComponentToStep,
    dto: String = DTONames.connComponentToStep,
    result: ConnComponentToStepResultSC
)

object ConnComponentToStepSC{
  implicit val format = Json.writes[ConnComponentToStepSC]
}