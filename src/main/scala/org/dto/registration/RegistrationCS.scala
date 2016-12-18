/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * {jsonId : 1, dto : Registeration, params : {username : test, password : test}}
 */

case class RegistrationCS (
                      jsonId: Int = DTOIds.registerId,
                      dto: String = DTONames.register,
                      params: RegistrationParamsCS
                    )

object RegistrationCS {
  implicit val format = Json.reads[RegistrationCS]
}
