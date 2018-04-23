package org.genericConfig.admin.models.json.config

import org.genericConfig.admin.shared.json.JsonNames
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class JsonDeleteConfigIn (
    json: String = JsonNames.DELET_CONFIG
)

object JsonDeleteConfigIn {
  implicit val format = Json.reads[JsonDeleteConfigIn]
}