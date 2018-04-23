package org.genericConfig.admin.models.json.dependency

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2017
 */

case class JsonDependencyOut (
    json: String = JsonNames.CREATE_DEPENDENCY,
    result: JsonDependencyResult
)

object JsonDependencyOut {
  implicit val format = Json.writes[JsonDependencyOut]
}