package org.genericConfig.admin.shared.user.json

import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.Format

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonUserResult (
    userId: Option[String], 
    username:Option[String], 
    status: JsonUserStatus
)

object JsonUserResult {
  implicit val format: Format[JsonUserResult] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
    (JsPath \ "username").format(Format.optionWithNull[String]) and 
    (JsPath \ "status").format(Format.of[JsonUserStatus])
  )(JsonUserResult.apply, unlift(JsonUserResult.unapply))
}
