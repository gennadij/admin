package org.genericConfig.admin.models.json.login

import play.api.libs.json.Json
import play.api.libs.json.Writes
import org.genericConfig.admin.shared.json.JsonNames

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
  implicit val format: Writes[JsonLoginOut] = Json.writes[JsonLoginOut]
}
