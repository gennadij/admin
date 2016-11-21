package org.dto.ClientServer.register

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class Register (
                      jsonId: Int = 1,
                      val method: String = "register",
                      val params: RegisterParams
                    )

object Register {
  implicit val format = Json.reads[Register]
}
