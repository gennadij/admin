/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configUri

import play.api.libs.json.Json

/**
 * {jsond : 3, dto : ConfigUri, params : {status : true, message : Nachricht}
 */
case class ConfigUriResultSC (
  status: Boolean,
  message: String
)

object ConfigUriResultSC {
  implicit val format = Json.writes[ConfigUriResultSC]
}