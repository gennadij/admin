package org.dto.configTree

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 6, dto : ConfigTree, result: {message : Nachricht, steps : 
 *         [ {stepId :#19:1, kind: first, components:
 *         [{componentId : #21:0, kind : immutable}]}]}}
 */

case class ConfigTreeResultSC (
    steps: Set[ConfigTreeStepSC],
    message: String
)

object ConfigTreeResultSC {
  implicit val format = Json.writes[ConfigTreeResultSC]
}