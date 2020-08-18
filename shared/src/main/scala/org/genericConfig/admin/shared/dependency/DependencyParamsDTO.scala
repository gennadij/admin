package org.genericConfig.admin.shared.dependency

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.08.2020
 */
case class DependencyParamsDTO (
  configProperties : Option[DependencyConfigPropertiesDTO] = None,
  userProperties : Option[DependencyUserPropertiesDTO] = None
)

object DependencyParamsDTO {
  implicit val format: Format[DependencyParamsDTO] = (
    (JsPath \ "configProperties").format(Format.optionWithNull[DependencyConfigPropertiesDTO]) and
    (JsPath \ "userProperties").format(Format.optionWithNull[DependencyUserPropertiesDTO])
  )(DependencyParamsDTO.apply, unlift(DependencyParamsDTO.unapply))
}