package org.genericConfig.admin.shared.json.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class CreateConfigResult (
    configId: String,
    status: JsonConfigStatus
)

object CreateConfigResult{
  implicit val format = Json.format[CreateConfigResult]
}