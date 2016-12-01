/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.ConfigTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

case class ConfigTreeCS (
    jsonId: Int,
    dto: String,
    params: ConfigTreeParamsCS
)

object ConfigTreeCS {
  implicit val format = Json.reads[ConfigTreeCS]
}