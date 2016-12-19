/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.firstStep

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * result : {stepId : #12:1, status : true, message : Nachricht}
 */

case class FirstStepResultSC (
  stepId: String,
  kind: String,
  message: String
  
)

object FirstStepResultSC {
  implicit val fortmat = Json.writes[FirstStepResultSC]
}