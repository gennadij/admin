/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.connStepToComponent

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * Verbindet Step mit Component
 * 
 * {jsonId : 9, dto : StepToComponent, params : {adminId : 40:0, outStepId : #40:0, inComponentId : #40:0}}
 */

case class ConnStepToComponentCS (
    jsonId: Int = DTOIds.connStepToComponen,
    dto: String = DTONames.connSteptoComponent,
    params: ConnStepToComponentParamsCS
)

object ConnStepToComponentCS {
  implicit val format = Json.reads[ConnStepToComponentCS]
}

