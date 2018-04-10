package org.genericConfig.admin.models.json.login

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonLoginParams (
    username: String,
    password: String
)

object JsonLoginParams {
  implicit val fortmat = Json.reads[JsonLoginParams]
}