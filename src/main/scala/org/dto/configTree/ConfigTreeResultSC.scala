package org.dto.configTree

import play.api.libs.json.Json

case class ConfigTreeResultSC (
    steps: Seq[ConfigTreeStepSC],
    message: String
)

object ConfigTreeResultSC {
  implicit val format = Json.writes[ConfigTreeResultSC]
}