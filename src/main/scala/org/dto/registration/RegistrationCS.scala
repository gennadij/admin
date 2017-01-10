/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto.registration

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Created by Gennadi Heimann 19.12.2016
 * {jsonId : 1, dto : Registeration, params : {username : test, password : test}}
 */

case class RegistrationCS (
                      jsonId: Int = DTOIds.REGISTRATION,
                      dto: String = DTONames.REGISTRATION,
                      params: RegistrationParamsCS
                    )

object RegistrationCS {
  implicit val format = Json.reads[RegistrationCS]
}
