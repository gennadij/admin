package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 06.06.2020
  */
case class ConfigGraphDTO (
                          action : String,
                          params : Option[ConfigGraphParamsDTO] = None,
                          result : Option[ConfigGraphResultDTO] = None
                          )

object ConfigGraphDTO  {
  implicit val format: Format[ConfigGraphDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
    (JsPath \ "params").format(Format.optionWithNull[ConfigGraphParamsDTO]) and
    (JsPath \ "result").format(Format.optionWithNull[ConfigGraphResultDTO])
  )(ConfigGraphDTO.apply, unlift(ConfigGraphDTO.unapply))
}
