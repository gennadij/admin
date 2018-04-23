package org.genericConfig.admin.models.json.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class GetConfigsResult (
    configId: String,
    configUrl: String,
    status: JsonConfigStatus
)

object GetConfigsResult{
  implicit val writer = Json.writes[GetConfigsResult]
}