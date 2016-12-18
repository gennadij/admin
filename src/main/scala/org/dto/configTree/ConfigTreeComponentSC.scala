/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configTree

import play.api.libs.json.Json

case class ConfigTreeComponentSC (
    id: String,
    componentId: String,
    adminId: String,
    //immutable, mutable
    kind: String,
    nextSteps: String
)

object ConfigTreeComponentSC {
  implicit val format = Json.writes[ConfigTreeComponentSC]
}