package org.dto.ServerClient.register

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class Register (
    jsonid: Int = 1,
    val dto: String = "Register",
    result: ResultRegister
)

object Register {
  implicit val format = Json.reads[Register]
}
