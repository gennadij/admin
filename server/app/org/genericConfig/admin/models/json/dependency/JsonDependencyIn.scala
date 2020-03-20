package org.genericConfig.admin.models.json.dependency

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2017
 */

case class JsonDependencyIn (
    json: String = JsonNames.CREATE_DEPENDENCY,
    params: JsonDependencyParams
)

object JsonDependencyIn {
  implicit val format = Json.reads[JsonDependencyIn]
}