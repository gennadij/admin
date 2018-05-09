package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonCreateConfigResult (
    userId: String,
    configId: String,
    status: JsonConfigStatus
)

object JsonCreateConfigResult{
  implicit val format = Json.format[JsonCreateConfigResult]
}