package org.genericConfig.admin.models.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann Jun 11, 2017
 */
 
case class DTOStatus (status: String, message: String)

object DTOStatus{
  implicit val format = Json.writes[DTOStatus]
}