/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */

package org.dto

import play.api.libs.json.Json

/**
  * Created by gennadi on 20.11.16.
  */
case class RegistrationParamsCS (
                          username: String,
                          password: String
                          )


object RegistrationParamsCS {
  implicit val format = Json.reads[RegistrationParamsCS]
}