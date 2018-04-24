package org.genericConfig.admin.shared.json.login

import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import play.api.libs.json.Format

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
implicit val writerJsonLoginResult: Format[JsonLoginResult] = (
    (JsPath \ "adminId").format(Format.of[String]) and
    (JsPath \ "username").format(Format.of[String]) and
    (JsPath \ "configs").format(Format.optionWithNull[List[JsonConfig]]) and
    (JsPath \ "status").format(Format.of[JsonLoginStatus])
  )(JsonLoginResult.apply, unlift(JsonLoginResult.unapply))
}
