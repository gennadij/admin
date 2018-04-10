package org.genericConfig.admin.models.json.login

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann on 22.11.16.
 */
case class JsonLoginResult (
    adminId: String,
    username: String,
    configs: List[JsonConfig],
    status: String,
    message: String
)

object JsonLoginResult{
  implicit val format = Json.writes[JsonLoginResult]
}
