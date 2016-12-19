/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.connStepToComponent

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * result :  {status: true, message : Nachricht}}
 */

case class ConnStepToComponentResultSC (
  status: Boolean,
  message: String
)

object ConnStepToComponentResultSC {
  implicit val format = Json.writes[ConnStepToComponentResultSC]
}