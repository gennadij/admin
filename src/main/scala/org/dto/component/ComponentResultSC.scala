package org.dto.component

import play.api.libs.json.Json

case class ComponentResultSC (
    id: String,
    componentId: String
    ,adminId: String
    ,kind: String
    ,stepId: String
)

object ComponentResultSC {
  implicit val format = Json.writes[ComponentResultSC]
}