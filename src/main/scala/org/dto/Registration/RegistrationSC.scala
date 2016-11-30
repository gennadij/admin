/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.Registration

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
  * Created by gennadi on 20.11.16.
  */
case class RegistrationSC (
    jsonId: Int = DTOIds.registerId,
    val dto: String = DTONames.register,
    result: RegistrationResultSC
)

object RegistrationSC {
  implicit val format = Json.writes[RegistrationSC]
}
