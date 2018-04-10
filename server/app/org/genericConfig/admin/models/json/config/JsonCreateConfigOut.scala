package org.genericConfig.admin.models.json.config

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */
case class JsonCreateConfigOut (
    jsonId: Int = DTOIds.CREATE_CONFIG,
    dto: String = DTONames.CREATE_CONFIG,
    result: CreateConfigResult
)

object JsonCreateConfigOut {
  implicit val format = Json.writes[JsonCreateConfigOut]
}