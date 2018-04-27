package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 */

case class JsonFirstStepIn (
    json: String = JsonNames.CREATE_FIRST_STEP,
    params: JsonFirstStepParams
    
)

object JsonFirstStepIn {
  implicit val format = Json.reads[JsonFirstStepIn]
}