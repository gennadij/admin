package org.dto.config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi heimann 09.01.2017
 */

case class CreateConfigParams (
    adminId: String,
    configUrl: String
)

object CreateConfigParams {
  implicit val format = Json.reads[CreateConfigParams]
}