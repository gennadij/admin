package org.dto.component

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class ComponentSC (
    dtoId: Int = DTOIds.CREATE_COMPONENT,
    dto: String = DTONames.CREATE_COMPONENT,
    result: ComponentResult
)

object ComponentSC {
  implicit val format = Json.writes[ComponentSC]
}