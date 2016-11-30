package org.dto.ConfigTree

import play.api.libs.json.Json

case class ConfigTreeResultSC (
    steps: Seq[ConfigTreeStepSC]
)

object ConfigTreeResultSC {
  implicit val format = Json.writes[ConfigTreeResultSC]
}