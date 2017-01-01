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
 * {jsond : 3, dto : ConfigUri, params : {adminId : #40:0, configUri : test.test.org}
 */

case class ConfigUriCS (
		jsonId : Int = DTOIds.configUri,
		dto: String = DTONames.configUri,
		params : ConfigUriParamsCS
)

object ConfigUri {
  implicit val format = Json.reads[ConfigUriCS]
}