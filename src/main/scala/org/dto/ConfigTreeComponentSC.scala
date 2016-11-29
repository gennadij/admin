package org.dto

import play.api.libs.json.Json

case class ConfigTreeComponentSC (
    id: String,
    componentId: String,
    adminId: String,
    //immutable, mutable
    kind: String,
    nextSteps: String
)

object ConfigTreeComponentSC {
  implicit val format = Json.writes[ConfigTreeComponentSC]
}