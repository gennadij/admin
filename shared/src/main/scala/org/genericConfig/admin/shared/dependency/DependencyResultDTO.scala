package org.genericConfig.admin.shared.dependency

import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.08.2020
 */
case class DependencyResultDTO (
  configProperties : Option[DependencyConfigPropertiesDTO] = None,
  userProperties : Option[DependencyUserPropertiesDTO] = None,
  errors: Option[List[ErrorDTO]] = None
)

object DependencyResultDTO {
  implicit val format: Format[DependencyResultDTO] = (
    (JsPath \ "configProperties").format(Format.optionWithNull[DependencyConfigPropertiesDTO]) and
      (JsPath \ "userProperties").format(Format.optionWithNull[DependencyUserPropertiesDTO]) and
      (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
    )(DependencyResultDTO.apply, unlift(DependencyResultDTO.unapply))
}