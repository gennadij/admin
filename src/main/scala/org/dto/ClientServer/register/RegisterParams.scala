package org.dto.ClientServer.register

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegisterParams (
                          username: String,
                          password: String
                          )


object RegisterParams {
  implicit val format = Json.reads[RegisterParams]
}