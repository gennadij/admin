package org.genericConfig.admin.shared.config.json

import org.genericConfig.admin.shared.common.json.JsonNames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.05.2018
 */
case class JsonUpdateConfigOut (
    json: String = JsonNames.UPDATE_CONFIG, 
    result: JsonUpdateConfigResult
)

object JsonUpdateConfigOut {
  implicit val format = Json.format[JsonUpdateConfigOut]
}