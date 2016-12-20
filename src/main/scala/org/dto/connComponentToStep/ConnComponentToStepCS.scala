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
 * {jsonId : 11, dto : ConnComponentToStep, params : ...}
 */

case class ConnComponentToStepCS (
    jsonId: Int = DTOIds.connComponentToStep,
    dto: String = DTONames.connComponentToStep,
    params: ConnComponentToStepParamsCS
)

object ConnComponentToStepCS {
  implicit val format = Json.reads[ConnComponentToStepCS]
}