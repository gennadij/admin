package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 *  Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonStepIn (
    json: String = JsonNames.CREATE_STEP,
    params: JsonStepParams
)


object JsonStepIn {
  implicit val format = Json.reads[JsonStepIn]
}