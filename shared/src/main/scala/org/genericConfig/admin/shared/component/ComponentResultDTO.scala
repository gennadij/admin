package org.genericConfig.admin.shared.component

import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */
case class ComponentResultDTO (
                                configProperties : Option[ComponentConfigPropertiesDTO] = None,
                                userProperties : Option[ComponentUserPropertiesDTO] = None,
                                errors: Option[List[ErrorDTO]] = None
                              )

object ComponentResultDTO {
  implicit val format: Format[ComponentResultDTO] = (
    (JsPath \ "configProperties").format(Format.optionWithNull[ComponentConfigPropertiesDTO]) and
    (JsPath \ "userProperties").format(Format.optionWithNull[ComponentUserPropertiesDTO])and
    (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
  )(ComponentResultDTO.apply, unlift(ComponentResultDTO.unapply))
}

