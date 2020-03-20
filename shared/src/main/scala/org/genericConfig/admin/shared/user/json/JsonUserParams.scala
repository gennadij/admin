package org.genericConfig.admin.shared.user.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */
case class JsonUserParams (
    username: String, 
    password: String
) 


object JsonUserParams {
  implicit val format = Json.format[JsonUserParams]
}