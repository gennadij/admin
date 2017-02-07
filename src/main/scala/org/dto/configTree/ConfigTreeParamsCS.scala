package org.dto.configTree

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class ConfigTreeParamsCS (
    configId: String
)

object ConfigTreeParamsCS {
  implicit val format = Json.reads[ConfigTreeParamsCS]
}