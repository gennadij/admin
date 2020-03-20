package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class JsonGetConfigsResult (
    userId: String, 
    configs: List[JsonConfig], 
    status: JsonConfigStatus
)

object JsonGetConfigsResult{
  implicit val format = Json.format[JsonGetConfigsResult]
}