package org.dto.ConfigTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

case class ConfigTreeSC (
    jsonId: Int = DTOIds.configTreeId,
    dto: String = DTONames.configTree,
    params: ConfigTreeResultSC
)

object ConfigTreeSC {
  implicit val format = Json.writes[ConfigTreeSC]
}