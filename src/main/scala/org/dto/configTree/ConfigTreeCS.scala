package org.dto.configTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016.
 */

case class ConfigTreeCS (
    dtoId: Int,
    dto: String,
    params: ConfigTreeParamsCS
)

object ConfigTreeCS {
  implicit val format = Json.reads[ConfigTreeCS]
}