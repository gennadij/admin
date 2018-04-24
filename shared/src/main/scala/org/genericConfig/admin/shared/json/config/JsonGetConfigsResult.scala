package org.genericConfig.admin.shared.json.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class JsonGetConfigsResult (
    configs: List[JsonConfig],
    status: JsonConfigStatus
)

object JsonGetConfigsResult{
  implicit val format = Json.format[JsonGetConfigsResult]
}