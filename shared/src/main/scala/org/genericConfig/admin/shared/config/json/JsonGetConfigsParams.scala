package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class JsonGetConfigsParams (
    userId: String
)

object JsonGetConfigsParams {
  implicit val format = Json.format[JsonGetConfigsParams]
}