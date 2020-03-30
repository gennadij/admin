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
                             userId: Option[String],
                             configId : Option[String] = None,
                             configUrl: Option[String] = None,
                             configurationCourse : Option[String] = None,
                             update : Option[ConfigUpdateDTO] = None
                           )

object ConfigParamsDTO {
  implicit val format : Format[ConfigParamsDTO] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
    (JsPath \ "configId").format(Format.optionWithNull[String]) and
    (JsPath \ "configUrl").format(Format.optionWithNull[String]) and
    (JsPath \ "configurationCourse").format(Format.optionWithNull[String]) and
    (JsPath \ "update").format(Format.optionWithNull[ConfigUpdateDTO])
  )(ConfigParamsDTO.apply, unlift(ConfigParamsDTO.unapply))
}