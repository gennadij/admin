package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonAddConfigResult (
    userId: String,
    configId: String,
    status: JsonConfigStatus
)

object JsonAddConfigResult{
  implicit val format = Json.format[JsonAddConfigResult]
}