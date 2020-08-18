package org.genericConfig.admin.shared.dependency

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.08.2020
 */
case class DependencyConfigPropertiesDTO(
  outId: Option[String] = None,
  inId: Option[String] = None,
  dependencyId: Option[String] = None,
  dependencyType: Option[String] = None
)

object DependencyConfigPropertiesDTO {
  implicit val format: Format[DependencyConfigPropertiesDTO] = (
    (JsPath \ "outId").format(Format.optionWithNull[String]) and
    (JsPath \ "inId").format(Format.optionWithNull[String]) and
    (JsPath \ "dependencyId").format(Format.optionWithNull[String]) and
    (JsPath \ "dependencyType").format(Format.optionWithNull[String])
  )(DependencyConfigPropertiesDTO.apply, unlift(DependencyConfigPropertiesDTO.unapply))
}
