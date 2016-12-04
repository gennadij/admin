package org.dto.component

import play.api.libs.json.Json

// {"jsonId": 5, "method": "addComponent", "params": {"adminId": "AU#40:0", "kind": "immutable", "stepId": "#12:1"}
case class ComponentParamsCS (
    adminId: String,
    kind: String
    ,stepId: String
)

object ComponentParamsCS {
  implicit val format = Json.reads[ComponentParamsCS]
}