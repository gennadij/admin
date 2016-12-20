/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * params : {adminId : #40:0, kind : default}
 */

case class StepParamsCS (
    adminId: String,
    kind: String
)


object StepParamsCS {
  implicit val format = Json.reads[StepParamsCS]
}
