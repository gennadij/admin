package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegisterSC (
    jsonid: Int = 1,
    val dto: String = "Register",
    result: ResultRegisterSC
)

object RegisterSC {
  implicit val format = Json.writes[RegisterSC]
}
