package org.genericConfig.admin.models.json.config

import play.api.libs.json.Json
import org.genericConfig.admin.shared.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonCreateConfigIn (
    json: String =JsonNames.CREATE_CONFIG,
    params: CreateConfigParams
)

object JsonCreateConfigIn {
  implicit val format = Json.reads[JsonCreateConfigIn]
}