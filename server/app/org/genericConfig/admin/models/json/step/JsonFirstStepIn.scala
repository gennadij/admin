package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 */

case class JsonFirstStepIn (
    dtoId: Int = DTOIds.CREATE_FIRST_STEP,
    dto: String = DTONames.CREATE_FIRST_STEP,
    params: JsonFirstStepParams
    
)

object JsonFirstStepIn {
  implicit val format = Json.reads[JsonFirstStepIn]
}