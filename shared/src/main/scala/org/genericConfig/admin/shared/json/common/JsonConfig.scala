package org.genericConfig.admin.shared.json.common

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 13.01.17
 */
case class JsonConfig (
    configId: String,
    configUrl: String
)

object JsonConfig{
  implicit val format = Json.format[JsonConfig]
}