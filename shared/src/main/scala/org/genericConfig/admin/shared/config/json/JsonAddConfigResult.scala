package org.genericConfig.admin.shared.config.json

import play.api.libs.json.Json
import play.api.libs.json.Format
import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2017
 */

case class JsonAddConfigResult (
    userId: Option[String] = None,
    configId: Option[String] = None,
    status: JsonConfigStatus
)

object JsonAddConfigResult{
  implicit val format: Format[JsonAddConfigResult] = (
      (JsPath \ "userId").format(Format.optionWithNull[String]) and
      (JsPath \ "configId").format(Format.optionWithNull[String]) and
      (JsPath \ "status").format(Format.of[JsonConfigStatus])
  )(JsonAddConfigResult.apply, unlift(JsonAddConfigResult.unapply))
}