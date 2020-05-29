package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 */

case class JsonConfigTreeOut (
    result: JsonConfigTreeResult
)

object JsonConfigTreeOut {
  implicit val format = Json.format[JsonConfigTreeOut]
}

