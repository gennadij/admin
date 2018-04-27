package org.genericConfig.admin.shared.configTree.json

import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonConfigTreeResult (
    step: Option[JsonConfigTreeStep],
    status: String,
    message: String
)

object JsonConfigTreeResult {
  
  implicit val writes: Writes[JsonConfigTreeResult] = (
    (JsPath \ "step").write(Writes.optionWithNull[JsonConfigTreeStep]) and
    (JsPath \ "status").write[String] and
    (JsPath \ "message").write[String]
  )(unlift(JsonConfigTreeResult.unapply))
}