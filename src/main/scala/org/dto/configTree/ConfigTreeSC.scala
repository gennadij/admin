package org.dto.configTree

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

case class ConfigTreeSC (
    jsonId: Int = DTOIds.configTreeId,
    dto: String = DTONames.configTree,
    result: ConfigTreeResultSC
)

object ConfigTreeSC {
  implicit val format = Json.writes[ConfigTreeSC]
}