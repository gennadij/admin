package org.genericConfig.admin.models.json.registration

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class RegistrationResult (
    adminId: String,
    username: String,
    status: String,
    message: String
)

object RegistrationResult {
  implicit val format = Json.writes[RegistrationResult]
}