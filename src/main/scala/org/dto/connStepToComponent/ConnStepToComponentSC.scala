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
 * {jsonId : 9, dto : ConnStepToComponent, result :  {status: true, message : Nachricht}}
 */

case class ConnStepToComponentSC (
    jsonId: Int = DTOIds.connStepToComponen,
    dto: String = DTONames.connSteptoComponent,
    params: ConnStepToComponentResultSC
)

object ConnStepToComponentSC {
  implicit val format = Json.writes[ConnStepToComponentSC]
}