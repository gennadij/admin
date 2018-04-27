package org.genericConfig.admin.shared.login.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi Heimann on 20.11.16.
 */

/*
{
  jsond : 2, 
  dto : Login, 
  params : {
    username : test, 
    password : test
  }
}
*/
case class JsonLoginIn (
    json: String = JsonNames.LOGIN, 
    params: JsonLoginParams
)

object JsonLoginIn {
  implicit val fortmat = Json.format[JsonLoginIn]
}