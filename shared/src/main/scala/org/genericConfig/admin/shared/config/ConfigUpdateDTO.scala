package org.genericConfig.admin.shared.config

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 23.03.2020
 */
case class ConfigUpdateDTO (
                           configUrl : Option[String],
                           configurationCourse : Option[String]
                           )

object ConfigUpdateDTO {
  implicit val format : Format[ConfigUpdateDTO] = (
    (JsPath \ "configUrl").format(Format.optionWithNull[String]) and
    (JsPath \ "configurationCourse").format(Format.optionWithNull[String])
    )(ConfigUpdateDTO.apply, unlift(ConfigUpdateDTO.unapply))
}
