package org.genericConfig.admin.models.json.component

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonComponentOut (
    dtoId: Int = DTOIds.CREATE_COMPONENT,
    dto: String = DTONames.CREATE_COMPONENT,
    result: ComponentResult
)

object JsonComponentOut {
  implicit val format = Json.writes[JsonComponentOut]
}