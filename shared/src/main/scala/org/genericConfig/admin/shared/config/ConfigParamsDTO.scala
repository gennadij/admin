package org.genericConfig.admin.shared.config

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 23.03.2020
  */
case class ConfigParamsDTO (
                             userId: String,
                             configUrl: String
                           )

object ConfigParamsDTO {
  implicit val format : Format[ConfigParamsDTO] = (
    (JsPath \ "userId").format(Format.of[String]) and
      (JsPath \ "configUrl").format(Format.of[String])
    )(ConfigParamsDTO.apply, unlift(ConfigParamsDTO.unapply))
}