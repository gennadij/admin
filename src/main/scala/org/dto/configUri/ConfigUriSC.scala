/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configUri

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 1.1.2017
 * 
 * {jsond : 3, dto : ConfigUri, params : {status : true, message : Nachricht}
 */
case class ConfigUriSC (
    jsonId: Int,
    dto: String,
    result: ConfigUriResultSC
)

object ConfigUriSC {
  implicit val format = Json.writes[ConfigUriSC]
}