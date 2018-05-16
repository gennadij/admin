package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi heimann 09.01.2017
 */

case class JsonAddConfigParams (
    adminId: String, 
    configUrl: String
)

object JsonAddConfigParams {
  implicit val format = Json.format[JsonAddConfigParams]
}