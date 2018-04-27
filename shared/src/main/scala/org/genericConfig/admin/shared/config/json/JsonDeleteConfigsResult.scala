package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class JsonDeleteConfigsResult (
    status: JsonConfigStatus
)

object JsonDeleteConfigsResult {
  implicit val format = Json.format[JsonDeleteConfigsResult]
}