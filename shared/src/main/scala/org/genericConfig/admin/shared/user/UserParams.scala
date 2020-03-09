package org.genericConfig.admin.shared.user

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.03.2020
 */
case class UserParams (
  username : String,
  password : String
)

object UserParams {
  implicit  val format = Json.format[UserParams]
}