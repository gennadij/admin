package org.genericConfig.admin.shared.component

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */
case class ComponentConfigPropertiesDTO (
                                     componentId: Option[String] = None,
                                     stepId: Option[String] = None,
                                     kind: Option[String] = None
                                   )

object ComponentConfigPropertiesDTO {
  implicit val format: Format[ComponentConfigPropertiesDTO] = (
    (JsPath \ "componentId").format(Format.optionWithNull[String]) and
      (JsPath \ "stepId").format(Format.optionWithNull[String]) and
      (JsPath \ "kind").format(Format.optionWithNull[String])
    )(ComponentConfigPropertiesDTO.apply, unlift(ComponentConfigPropertiesDTO.unapply))
}
