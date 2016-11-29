/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
  */
case class LoginSC (
                   jsonId: Int = DTONames.loginId,
                   dto: String = DTONames.login,
                   result: LoginResultSC
                   )

object LoginSC {
  implicit val format = Json.writes[LoginSC]
}
