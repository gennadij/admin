package org.genericConfig.admin.shared.json.login

import play.api.libs.json.Json
import play.api.libs.json.Writes
import org.genericConfig.admin.shared.json.JsonNames
import play.api.libs.json.Format

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 22.11.16.
 */

case class JsonLoginOut (
    json: String = JsonNames.LOGIN,
    result: JsonLoginResult
)

object JsonLoginOut {
  implicit val format: Format[JsonLoginOut] = Json.format[JsonLoginOut]
}
