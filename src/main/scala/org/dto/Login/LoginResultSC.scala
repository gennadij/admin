/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.Login

import play.api.libs.json.Json

/**
  * Created by gennadi on 22.11.16.
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
