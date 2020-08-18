package org.genericConfig.admin.shared.dependency

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.08.2020
 */
case class DependencyUserPropertiesDTO(
  nameToShow: Option[String] = None,
  visualization: Option[String] = None,
  description : Option[String] = None
)

object DependencyUserPropertiesDTO {
  implicit val format: Format[DependencyUserPropertiesDTO] = (
    (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
    (JsPath \ "visualization").format(Format.optionWithNull[String]) and
    (JsPath \ "description").format(Format.optionWithNull[String])
  )(DependencyUserPropertiesDTO.apply, unlift(DependencyUserPropertiesDTO.unapply))
}