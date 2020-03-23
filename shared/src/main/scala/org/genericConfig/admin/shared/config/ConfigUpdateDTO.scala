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
                           dummy : Option[String],
                           dummy2 : Option[String]
                           )

object ConfigUpdateDTO {
  implicit val format : Format[ConfigUpdateDTO] = (
    (JsPath \ "dummy").format(Format.optionWithNull[String]) and
    (JsPath \ "dummy2").format(Format.optionWithNull[String])
    )(ConfigUpdateDTO.apply, unlift(ConfigUpdateDTO.unapply))
}
