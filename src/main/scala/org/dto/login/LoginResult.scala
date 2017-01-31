/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.login

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann on 22.11.16.
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
