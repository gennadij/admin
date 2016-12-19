/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configTree

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 6, dto : ConfigTree, result: {message : Nachricht, steps : 
 *         [ {stepId :#19:1, kind: first, components:
 *         [{componentId : #21:0, kind : immutable}]}]}}
 */

case class ConfigTreeStepSC (
    stepId: String,
    kind: String,
    components: List[ConfigTreeComponentSC]
)

object ConfigTreeStepSC {
  implicit val format = Json.writes[ConfigTreeStepSC]
}