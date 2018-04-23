package org.genericConfig.admin.models.json.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 20.04.2018
 */
case class GetConfigsParams (
    userId: String
)

object GetConfigsParams {
  implicit val format = Json.reads[GetConfigsParams]
}