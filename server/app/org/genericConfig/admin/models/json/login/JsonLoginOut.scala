package org.genericConfig.admin.models.json.login

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Writes

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 22.11.16.
 */

case class JsonLoginOut (
    json: String = DTONames.LOGIN,
    result: JsonLoginResult
)

object JsonLoginOut {
  implicit val format: Writes[JsonLoginOut] = Json.writes[JsonLoginOut]
}
