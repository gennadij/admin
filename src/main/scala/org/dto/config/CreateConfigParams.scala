package org.dto.config

import play.api.libs.json.Json

/**
 * Created by Gennadi heimann 09.01.2017
 * 
 * params : {adminId : #40:0, configUrl : test.test.org}
 */

case class CreateConfigParams (
    adminId: String,
    configUrl: String
)

object CreateConfigParams {
  implicit val format = Json.reads[CreateConfigParams]
}