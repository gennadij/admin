/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.connStepToComponent

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * params : {adminId : #40:0, outStepId : #40:0, inComponentId : #40:0}
 */


case class ConnStepToComponentParamsCS (
    adminId: String,
    outStep: String,
    inComponent: String
)

object ConnStepToComponentParamsCS {
  implicit val format = Json.reads[ConnStepToComponentParamsCS]
}