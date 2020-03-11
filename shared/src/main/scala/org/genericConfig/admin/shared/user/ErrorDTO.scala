package org.genericConfig.admin.shared.user

import play.api.libs.json.{Format, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.03.2020
 */
case class ErrorDTO(
                 message: String,
                 name : String,
                 code : String
                 )

object ErrorDTO{
  implicit val format: Format[ErrorDTO] = Json.format[ErrorDTO]
}