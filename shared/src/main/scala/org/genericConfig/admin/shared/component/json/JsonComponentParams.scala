package org.genericConfig.admin.shared.component.json

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, JsPath, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 17.06.2018
 */
case class JsonComponentParams (
    componentId: Option[String] = None,
    stepId: Option[String] = None,
    nameToShow: Option[String] = None,
    kind: Option[String] = None
)

object JsonComponentParams {

  implicit val format: Format[JsonComponentParams] = (
      (JsPath \ "componentId").format(Format.optionWithNull[String]) and
      (JsPath \ "stepId").format(Format.optionWithNull[String]) and
      (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
      (JsPath \ "kind").format(Format.optionWithNull[String])
    )(JsonComponentParams.apply, unlift(JsonComponentParams.unapply))
}