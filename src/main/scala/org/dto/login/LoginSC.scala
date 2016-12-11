/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
  * Created by gennadi on 22.11.16.
  */
case class LoginSC (
                   jsonId: Int = DTOIds.loginId,
                   dto: String = DTONames.login,
                   result: LoginResultSC
                   )

object LoginSC {
  implicit val format = Json.writes[LoginSC]
}