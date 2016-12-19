/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.configTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Created by Gennadi Heimann 19.12.2016.
 * 
 * {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
 */

case class ConfigTreeCS (
    jsonId: Int,
    dto: String,
    params: ConfigTreeParamsCS
)

object ConfigTreeCS {
  implicit val format = Json.reads[ConfigTreeCS]
}