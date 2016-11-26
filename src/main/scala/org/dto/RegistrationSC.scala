package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegistrationSC (
    jsonId: Int = DTONames.registerId,
    val dto: String = DTONames.register,
    result: RegistrationResultSC
)

object RegistrationSC {
  implicit val format = Json.writes[RegistrationSC]
}
