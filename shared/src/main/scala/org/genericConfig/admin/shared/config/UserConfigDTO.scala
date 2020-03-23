package org.genericConfig.admin.shared.config

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 23.03.2020
 */
case class UserConfigDTO (
                           configId: Option[String],
                           configUrl: Option[String]
                         )

object UserConfigDTO{
  implicit val format: Format[UserConfigDTO] = (
    (JsPath \ "configId").format(Format.optionWithNull[String]) and
      (JsPath \ "configUrl").format(Format.optionWithNull[String])
    )(UserConfigDTO.apply, unlift(UserConfigDTO.unapply))
}
