package org.genericConfig.admin.models.json.login

import play.api.libs.json.Json
import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Writes

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by gennadi on 22.11.16.
 */

/*
{
  dtoId : 2, 
  dto: Login, 
  result: {
    adminId : #40:0, 
    username : test, 
    configs:[
      {
        configId: #12:23, 
        configUrl: http://config.de
      }...
    ], 
    status : SUCCESSFUL_CONFIG, 
    message : Nachricht
  }
}

*/
case class JsonLoginOut (
    dtoId: Int = DTOIds.LOGIN,
    dto: String = DTONames.LOGIN,
    result: JsonLoginResult
)

object JsonLoginOut {
  implicit val format: Writes[JsonLoginOut] = Json.writes[JsonLoginOut]
}
