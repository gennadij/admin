/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * {jsonId : 1, dto : Registeration, params : {username : test, password : test}}
 */
case class RegistrationParamsCS (
                          username: String,
                          password: String
                          )


object RegistrationParamsCS {
  implicit val format = Json.reads[RegistrationParamsCS]
}