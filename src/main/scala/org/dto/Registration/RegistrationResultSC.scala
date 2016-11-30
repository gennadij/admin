/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.Registration

import play.api.libs.json.Json

case class RegistrationResultSC (
    adminId: String,
    username: String,
    status: Boolean,
    message: String
)

object RegistrationResultSC {
  implicit val format = Json.writes[RegistrationResultSC]
}