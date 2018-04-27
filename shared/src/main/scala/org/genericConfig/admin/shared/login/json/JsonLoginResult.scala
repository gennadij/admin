package org.genericConfig.admin.shared.login.json

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.Format
import org.genericConfig.admin.shared.common.json.JsonConfig

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann on 22.11.16.
 */
case class JsonLoginResult (
    adminId: String, 
    username: String, 
    configs: List[JsonConfig], 
    status: JsonLoginStatus
)

object JsonLoginResult{
implicit val writerJsonLoginResult: Format[JsonLoginResult] = (
    (JsPath \ "adminId").format(Format.of[String]) and
    (JsPath \ "username").format(Format.of[String]) and
    (JsPath \ "configs").format(Format.of[List[JsonConfig]]) and
    (JsPath \ "status").format(Format.of[JsonLoginStatus])
  )(JsonLoginResult.apply, unlift(JsonLoginResult.unapply))
}
