package org.genericConfig.admin.shared.component

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */
case class ComponentParamsDTO(
                               configProperties : Option[ComponentConfigPropertiesDTO] = None,
                               userProperties : Option[ComponentUserPropertiesDTO] = None
                             )

object ComponentParamsDTO {
  implicit val format: Format[ComponentParamsDTO] = (
    (JsPath \ "configProperties").format(Format.optionWithNull[ComponentConfigPropertiesDTO]) and
    (JsPath \ "userProperties").format(Format.optionWithNull[ComponentUserPropertiesDTO])
    )(ComponentParamsDTO.apply, unlift(ComponentParamsDTO.unapply))
}