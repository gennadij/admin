/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto

import play.api.libs.json.Json

case class ConfigTreeCS (
    jsonId: Int = DTONames.configTreeId,
    dto: String = DTONames.configTree,
    params: ConfigTreeParamsCS
)

object ConfigTreeCS {
  implicit val format = Json.reads[ConfigTreeCS]
}