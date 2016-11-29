/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegistrationCS (
                      jsonId: Int = DTONames.registerId,
                      val dto: String = DTONames.register,
                      val params: RegistrationParamsCS
                    )

object RegistrationCS {
  implicit val format = Json.reads[RegistrationCS]
}
