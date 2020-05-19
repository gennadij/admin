package org.genericConfig.admin.shared.error

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
case class ErrorParamsDTO(
    message: String
)

object ErrorParamsDTO {
  implicit val format = Json.format[ErrorParamsDTO]
}