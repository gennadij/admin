package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonAddConfigIn (
    json: String =JsonNames.ADD_CONFIG, 
    params: JsonAddConfigParams
)
object JsonAddConfigIn {
  implicit val format = Json.format[JsonAddConfigIn]
}