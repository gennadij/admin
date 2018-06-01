package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 *  Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonStepIn (
    json: String,
    params: JsonStepParams
)


object JsonStepIn {
  implicit val format = Json.format[JsonStepIn]
}