package org.genericConfig.admin.shared.component

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */

case class ComponentUserPropertiesDTO(
                                   nameToShow: Option[String] = None,
                                   nickName : Option[String] = None
                                 )
object ComponentUserPropertiesDTO {
  implicit val format: Format[ComponentUserPropertiesDTO] = (
    (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
    (JsPath \ "nickName").format(Format.optionWithNull[String])
    )(ComponentUserPropertiesDTO.apply, unlift(ComponentUserPropertiesDTO.unapply))
}