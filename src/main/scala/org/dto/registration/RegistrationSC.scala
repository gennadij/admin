/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Created by Gennadi Heimann 19.12.2016
  * {jsonId : 1, dto : Registeration, result : {adminId : #40:0, username : test, status : true, message : Nachricht}}
  */
case class RegistrationSC (
    jsonId: Int = DTOIds.registerId,
    val dto: String = DTONames.register,
    result: RegistrationResultSC
)

object RegistrationSC {
  implicit val format = Json.writes[RegistrationSC]
}
