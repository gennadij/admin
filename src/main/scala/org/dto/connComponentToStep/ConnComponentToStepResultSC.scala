/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.connComponentToStep

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 20.12.2016
 * 
 * result : {status: true, message: Nachricht}
 */

case class ConnComponentToStepResultSC (
    status: Boolean,
    message: String
)

object ConnComponentToStepResultSC {
  implicit val format = Json.writes[ConnComponentToStepResultSC]
}