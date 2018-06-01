package org.genericConfig.admin.shared.user.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */


case class JsonUserIn (
    json: String = "", 
    params: JsonUserParams
)

object JsonUserIn {
  implicit val format = Json.format[JsonUserIn]
}
