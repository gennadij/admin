/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import play.api.libs.json.Json

/**
  * Created by Gennadi Heimann on 22.11.16.
  * {jsonId : 2, dto: Login, result: {adminId : #40:0, username : test, status : true, message : Nachricht}}
  */
case class LoginResult (
                          adminId: String,
                          username: String,
                          configs: List[Config],
                          status: Boolean,
                          message: String
                         )

object LoginResult{
  implicit val format = Json.writes[LoginResult]
}
