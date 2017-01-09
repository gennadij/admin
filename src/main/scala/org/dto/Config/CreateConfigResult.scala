package org.dto.Config

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 * 
 * result : {id: #23:12, status : true, message : Nachricht}
 */

case class CreateConfigResult (
    id: String,
    status: Boolean,
    message: String
)

object CreateConfigResult{
  implicit val format = Json.writes[CreateConfigResult]
}