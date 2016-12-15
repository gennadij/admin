package org.dto.nextStep

import play.api.libs.json.Json

case class NextStepResultSC (
    id: String,
    stepId: String,
    adminId: String,
    kind: String
)

object NextStepResultSC {
  implicit val format = Json.writes[NextStepResultSC]
}