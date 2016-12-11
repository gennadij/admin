/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import org.dto.DTONames
import play.api.libs.json.Json
import org.dto.DTOIds

/**
  * Created by gennadi on 20.11.16.
  */
case class LoginCS (
    jsonId: Int = DTOIds.loginId,
    dto: String = DTONames.login,
    params: LoginParamsCS
)

object LoginCS {
  implicit val fortmat = Json.reads[LoginCS]
}