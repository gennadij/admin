package org.genericConfig.admin.models.json.login

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

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
    dtoId: Int = DTOIds.LOGIN,
    dto: String = DTONames.LOGIN,
    params: JsonLoginParams
)

object JsonLoginIn {
  implicit val fortmat = Json.reads[JsonLoginIn]
}