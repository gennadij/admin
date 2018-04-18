package org.genericConfig.admin.models.json.login

import play.api.libs.functional.syntax._
import play.api.libs.json.Json
import play.api.libs.json.Writes
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann on 22.11.16.
 */
case class JsonLoginResult (
    adminId: String,
    username: String,
    configs: Option[List[JsonConfig]],
    status: JsonLoginStatus
)

object JsonLoginResult{
implicit val writerJsonLoginResult: Writes[JsonLoginResult] = (
    (JsPath \ "adminId").write(Writes.of[String]) and
    (JsPath \ "username").write(Writes.of[String]) and
    (JsPath \ "configs").write(Writes.optionWithNull[List[JsonConfig]]) and
    (JsPath \ "status").write(Writes.of[JsonLoginStatus])
  )(unlift(JsonLoginResult.unapply))
}
