/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * {jsond : 2, dto : Login, params : {username : test, password : test}}
 */

case class LoginParamsCS (
    username: String,
    password: String
)

object LoginParamsCS {
  implicit val fortmat = Json.reads[LoginParamsCS]
}