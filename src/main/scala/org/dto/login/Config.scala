package org.dto.login

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 13.01.17
 */
case class Config (
    configId: String,
    configUrl: String
)

object Config{
  implicit val format = Json.writes[Config]
}