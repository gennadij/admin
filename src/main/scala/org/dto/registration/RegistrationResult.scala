/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * {jsonId : 1, dto : Registeration, result : {adminId : #40:0, username : test, status : true, message : Nachricht}}
 */

case class RegistrationResult (
    adminId: String,
    username: String,
    status: Boolean,
    message: String
)

object RegistrationResult {
  implicit val format = Json.writes[RegistrationResult]
}