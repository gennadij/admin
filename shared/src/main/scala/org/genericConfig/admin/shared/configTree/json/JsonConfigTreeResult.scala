package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonConfigTreeResult (
    userId: Option[String],
    configId: Option[String],
    step: Option[JsonConfigTreeStep],
    status: JsonConfigTreeStatus
)

object JsonConfigTreeResult {
  
  implicit val writes: Format[JsonConfigTreeResult] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
    (JsPath \ "configId").format(Format.optionWithNull[String]) and
    (JsPath \ "step").format(Format.optionWithNull[JsonConfigTreeStep]) and
    (JsPath \ "status").format[JsonConfigTreeStatus] 
  )(JsonConfigTreeResult.apply, unlift(JsonConfigTreeResult.unapply))
}