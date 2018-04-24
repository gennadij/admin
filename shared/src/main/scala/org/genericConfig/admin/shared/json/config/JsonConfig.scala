package org.genericConfig.admin.shared.json.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 23.04.2018
 */
case class JsonConfig (
    configId: String,
    configUrl: String
)

object JsonConfig {
  implicit val format = Json.format[JsonConfig]
}