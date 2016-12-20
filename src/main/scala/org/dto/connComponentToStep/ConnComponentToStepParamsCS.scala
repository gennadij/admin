/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.connComponentToStep

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 20.12.2016
 * 
 * params : {adminId : #40:0, inStepId : #40:1, outComponentId : #40:2}
 */

case class ConnComponentToStepParamsCS (
    adminId: String,
    inStepId: String,
    outComponentId: String
)

object ConnComponentToStepParamsCS {
  implicit val format = Json.reads[ConnComponentToStepParamsCS]
}