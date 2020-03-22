package org.genericConfig.admin.shared.config

import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO, UserResultDTO}
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 23.03.2020
  */
case class ConfigDTO (
                  action : String = "",
                  params : String = Option[ConfigParamsDTO]

                )

object ConfigDTO {
  implicit val format : Format[ConfigDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
    (JsPath \ "action").format(Format.optionWithNull[ConfigParamsDTO])
  )(ConfigDTO.apply, unlift(ConfigDTO.unapply))
}