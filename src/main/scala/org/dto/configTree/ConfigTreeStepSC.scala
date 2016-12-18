/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configTree

import play.api.libs.json.Json

case class ConfigTreeStepSC (
    id: String,
    stepId: String,
    adminId: String,
    kind: String,
    components: List[ConfigTreeComponentSC]
)

object ConfigTreeStepSC {
  implicit val format = Json.writes[ConfigTreeStepSC]
}