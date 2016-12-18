/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json

/**
 * {jsonId : 1, dto : Registeration, result : {adminId : #40:0, username : test, status : true, message : Nachricht}}
 */

case class RegistrationResultSC (
    adminId: String,
    username: String,
    status: Boolean,
    message: String
)

object RegistrationResultSC {
  implicit val format = Json.writes[RegistrationResultSC]
}