/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class LoginCS (
    jsonId: Int = DTONames.loginId,
    dto: String = DTONames.login,
    params: LoginParamsCS
)

object LoginCS {
  implicit val fortmat = Json.reads[LoginCS]
}