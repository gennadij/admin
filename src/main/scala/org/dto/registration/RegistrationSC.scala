package org.dto.registration

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
  */
case class RegistrationSC (
    dtoId: Int = DTOIds.REGISTRATION,
    dto: String = DTONames.REGISTRATION,
    result: RegistrationResult
)

object RegistrationSC {
  implicit val format = Json.writes[RegistrationSC]
}
