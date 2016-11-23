package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegisterParamsCS (
                          username: String,
                          password: String
                          )


object RegisterParamsCS {
  implicit val format = Json.reads[RegisterParamsCS]
}