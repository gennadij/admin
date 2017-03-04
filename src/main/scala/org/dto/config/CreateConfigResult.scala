package org.dto.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class CreateConfigResult (
    configId: String,
    status: Boolean,
    message: String
)

object CreateConfigResult{
  implicit val format = Json.writes[CreateConfigResult]
}