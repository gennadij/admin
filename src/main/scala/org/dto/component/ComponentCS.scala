/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.component

import play.api.libs.json.Json
import org.dto.DTOIds
import org.dto.DTONames

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
 */
case class ComponentCS (
    jsonId: Int = DTOIds.COMPONENT,
    dto: String = DTONames.COMPONENT,
    params: ComponentParamsCS
)

object ComponentCS {
  implicit val format = Json.reads[ComponentCS]
}