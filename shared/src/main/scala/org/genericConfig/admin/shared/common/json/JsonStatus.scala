package org.genericConfig.admin.shared.common.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
case class JsonStatus (
    status: String, 
    message: String
)

object JsonStatus {
  implicit val formatJsonStatus = Json.format[JsonStatus]
}