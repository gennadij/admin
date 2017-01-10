/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.component

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 8, dto : Component, result : {componentId : #13:1, status : true, message : Nachricht}}
 */

case class ComponentSC (
    jsonId: Int = DTOIds.COMPONENT,
    dto: String = DTONames.COMPONENT,
    result: ComponentResultSC
)

object ComponentSC {
  implicit val format = Json.writes[ComponentSC]
}