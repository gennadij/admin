package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegisterCS (
                      jsonId: Int = 1,
                      val dto: String = "Register",
                      val params: RegisterParamsCS
                    )

object RegisterCS {
  implicit val format = Json.reads[RegisterCS]
}
