package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */


case class JsonUpdateConfigResult (
    userId: String,
    status: JsonConfigStatus
)

object JsonUpdateConfigResult{
  implicit val format = Json.format[JsonUpdateConfigResult]
}