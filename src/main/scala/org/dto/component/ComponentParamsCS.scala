/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.component

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 1.1.2017
 */
case class ComponentParamsCS (
    adminId: String,
    kind: String
//    ,stepId: String
)

object ComponentParamsCS {
  implicit val format = Json.reads[ComponentParamsCS]
}