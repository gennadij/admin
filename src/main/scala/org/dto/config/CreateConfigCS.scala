package org.dto.config

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class CreateConfigCS (
    jsonId: Int = DTOIds.CREATE_CONFIG,
    dto: String =DTONames.CREATE_CONFIG,
    params: CreateConfigParams
)

object CreateConfigCS {
  implicit val format = Json.reads[CreateConfigCS]
}