package org.dto.config

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 * 
 * jsond : 3, dto : CreateConfig, result : ...
 */
case class CreateConfigSC (
    jsonId: Int = DTOIds.CREATE_CONFIG,
    dto: String = DTONames.CREATE_CONFIG,
    result: CreateConfigResult
)

object CreateConfigSC {
  implicit val format = Json.writes[CreateConfigSC]
}