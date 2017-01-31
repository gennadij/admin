package org.dto.login

import play.api.libs.json.Json
import org.dto.DTONames
import org.dto.DTOIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 22.11.16.
 */
case class LoginSC (
    dtoId: Int = DTOIds.LOGIN,
    dto: String = DTONames.LOGIN,
    result: LoginResult
)

object LoginSC {
  implicit val format = Json.writes[LoginSC]
}
