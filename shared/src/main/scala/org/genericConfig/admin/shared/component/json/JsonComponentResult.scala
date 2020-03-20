package org.genericConfig.admin.shared.component.json

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, JsPath, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 */

case class JsonComponentResult (
    componentId: Option[String] = None,
    nameToShow: Option[String] = None,
    kind: Option[String] = None,
    status: JsonComponentStatus
)

object JsonComponentResult {
  implicit val format: Format[JsonComponentResult] = (
    (JsPath \ "componentId").format(Format.optionWithNull[String]) and
      (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
      (JsPath \ "kind").format(Format.optionWithNull[String]) and
      (JsPath \ "status").format(Format.of[JsonComponentStatus])
    )(JsonComponentResult.apply, unlift(JsonComponentResult.unapply))
}