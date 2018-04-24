package org.genericConfig.admin.shared.json.config

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
case class JsonCreateConfigOut (
    json: String = JsonNames.CREATE_CONFIG,
    result: CreateConfigResult
)

object JsonCreateConfigOut {
  implicit val format = Json.format[JsonCreateConfigOut]
}