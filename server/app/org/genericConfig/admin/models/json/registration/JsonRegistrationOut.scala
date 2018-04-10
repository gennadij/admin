package org.genericConfig.admin.models.json.registration

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
  */


case class JsonRegistrationOut (
    json: String = DTONames.REGISTRATION,
    result: RegistrationResult
)

object JsonRegistrationOut {
  implicit val format = Json.writes[JsonRegistrationOut]
}
