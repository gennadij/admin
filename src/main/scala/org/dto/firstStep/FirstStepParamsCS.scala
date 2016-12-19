/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.firstStep

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 *  params : {adminId : #40:0, kind  : first}
 */

case class FirstStepParamsCS (
  adminId: String,
  kind: String
)


object FirstStepParamsCS {
  implicit val fortmat = Json.reads[FirstStepParamsCS]
}