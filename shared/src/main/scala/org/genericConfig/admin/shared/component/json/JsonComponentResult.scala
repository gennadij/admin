package org.genericConfig.admin.shared.component.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 */

case class JsonComponentResult (
    componentId: String,
    status: JsonComponentStatus
)

object JsonComponentResult {
  implicit val format = Json.format[JsonComponentResult]
}