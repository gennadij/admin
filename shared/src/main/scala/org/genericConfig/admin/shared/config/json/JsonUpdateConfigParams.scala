package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.05.2018
 */
case class JsonUpdateConfigParams (
    configId: String,
    configUrl: String
)

object JsonUpdateConfigParams {
  implicit val format = Json.format[JsonUpdateConfigParams]
}