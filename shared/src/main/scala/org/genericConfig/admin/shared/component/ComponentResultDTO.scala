package org.genericConfig.admin.shared.component

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */
case class ComponentResultDTO (
                                configProperties : Option[ComponentConfigPropertiesDTO] = None,
                                userProperties : Option[ComponentUserPropertiesDTO] = None
                              )

object ComponentResultDTO {
  implicit val format: Format[ComponentResultDTO] = (
    (JsPath \ "configProperties").format(Format.optionWithNull[ComponentConfigPropertiesDTO]) and
      (JsPath \ "userProperties").format(Format.optionWithNull[ComponentUserPropertiesDTO])
    )(ComponentResultDTO.apply, unlift(ComponentResultDTO.unapply))
}

