package org.dto

import play.api.libs.json.Json

case class ConfigTreeSC (
    jsonId: Int = DTONames.configTreeId,
    dto: String = DTONames.configTree,
    params: ConfigTreeResultSC
)

object ConfigTreeSC {
  implicit val format = Json.writes[ConfigTreeSC]
}