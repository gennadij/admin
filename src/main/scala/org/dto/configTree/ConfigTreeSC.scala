/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 6, dto : ConfigTree, result: {message : Nachricht, steps : 
 *         [ {stepId :#19:1, kind: first, components:
 *         [{componentId : #21:0, kind : immutable}]}]}}
 */

case class ConfigTreeSC (
    dtoId: Int = DTOIds.CONFIG_TREE,
    dto: String = DTONames.CONFIG_TREE,
    result: ConfigTreeResultSC
)

object ConfigTreeSC {
  implicit val format = Json.writes[ConfigTreeSC]
}