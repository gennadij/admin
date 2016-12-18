/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.configUri

import play.api.libs.json.Json

/**
 *  {jsond : 3, dto : ConfigUri, params : {adminId : #40:0, configUri : test.test.org}
 */
case class ConfigUriParamsCS (
    adminId: String,
    configUri: String
)

object ConfigUriParamsCS {
  implicit val format = Json.reads[ConfigUriParamsCS]
}