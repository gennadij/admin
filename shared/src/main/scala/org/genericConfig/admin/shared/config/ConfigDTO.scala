package org.genericConfig.admin.shared.config

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 23.03.2020
  */
case class ConfigDTO (
                  action : String = "",
                  params : Option[ConfigParamsDTO],
                  result : Option[ConfigResultDTO]
                )

object ConfigDTO {
  implicit val format : Format[ConfigDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
    (JsPath \ "params").format(Format.optionWithNull[ConfigParamsDTO]) and
    (JsPath \ "result").format(Format.optionWithNull[ConfigResultDTO])
  )(ConfigDTO.apply, unlift(ConfigDTO.unapply))
}