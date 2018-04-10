package org.genericConfig.admin.models.json.dependency

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTONames
import org.genericConfig.admin.models.json.DTOIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2017
 */

case class JsonDependencyIn (
    dtoId: Int = DTOIds.CREATE_DEPENDENCY,
    dto: String = DTONames.CREATE_DEPENDENCY,
    params: JsonDependencyParams
)

object JsonDependencyIn {
  implicit val format = Json.reads[JsonDependencyIn]
}