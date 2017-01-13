package org.dto.login

import org.dto.DTONames
import play.api.libs.json.Json
import org.dto.DTOIds

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
  * Created by gennadi Heimann on 20.11.16.
  * {jsond : 2, dto : Login, params : {username : test, password : test}}
  */
case class LoginCS (
    dtoId: Int = DTOIds.LOGIN,
    dto: String = DTONames.LOGIN,
    params: LoginParams
)

object LoginCS {
  implicit val fortmat = Json.reads[LoginCS]
}