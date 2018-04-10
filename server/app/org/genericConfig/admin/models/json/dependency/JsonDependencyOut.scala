package org.genericConfig.admin.models.json.dependency

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2017
 */

case class JsonDependencyOut (
    dtoId: Int = DTOIds.CREATE_DEPENDENCY,
    dto: String = DTONames.CREATE_DEPENDENCY,
    result: JsonDependencyResult
)

object JsonDependencyOut {
  implicit val format = Json.writes[JsonDependencyOut]
}