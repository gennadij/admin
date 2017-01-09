/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.Config

import org.dto.DTOIds
import org.dto.DTONames
import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 09.01.2017
 * 
 * {jsond : 3, dto : CreateConfig, params : ...
 */

case class CreateConfigCS (
    jsonId: Int = DTOIds.createConfig,
    dto: String =DTONames.createConfig,
    params: CreateConfigParams
)

object CreateConfigCS {
  implicit val format = Json.reads[CreateConfigCS]
}