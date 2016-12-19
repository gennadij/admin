/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import play.api.libs.json.Json

/**
  * Created by Gennadi Heimann on 22.11.16.
  * {jsonId : 2, dto: Login, result: {adminId : #40:0, username : test, status : true, message : Nachricht}}
  */
case class LoginResultSC (
                          adminId: String,
                          username: String,
                          status: Boolean,
                          message: String
                         )

object LoginResultSC{
  implicit val format = Json.writes[LoginResultSC]
}
