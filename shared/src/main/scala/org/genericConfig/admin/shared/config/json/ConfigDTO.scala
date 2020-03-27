package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class ConfigDTO(
    json: String = JsonNames.DELET_CONFIG, 
    params: JsonDeleteConfigParams
)

object ConfigDTO {
  implicit val format = Json.format[ConfigDTO]
}