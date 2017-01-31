package org.dto.configTree

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class ConfigTreeStepSC (
    stepId: String,
    kind: String,
    components: Set[ConfigTreeComponentSC]
)

object ConfigTreeStepSC {
  implicit val format = Json.writes[ConfigTreeStepSC]
}