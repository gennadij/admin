package org.genericConfig.admin.shared.error.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
case class JsonErrorIn (
    json: String,
    params: JsonErrorParams
)

object JsonErrorIn {
  implicit val format = Json.format[JsonErrorIn]
}