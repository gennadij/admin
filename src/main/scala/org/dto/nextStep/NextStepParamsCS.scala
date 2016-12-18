/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.nextStep

import play.api.libs.json.Json



case class NextStepParamsCS (
    adminId: String,
    kind: String,
    loginStatus: Boolean
)


object NextStepParamsCS {
  implicit val format = Json.reads[NextStepParamsCS]
}

//"params": {"adminId": "AU#40:0", "kind": "default", "componentId": "#13:1"