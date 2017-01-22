/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configTree

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 6, dto : ConfigTree, params: {adminId : #40:0}}
 */

case class ConfigTreeParamsCS (
    configId: String
)

object ConfigTreeParamsCS {
  implicit val format = Json.reads[ConfigTreeParamsCS]
}