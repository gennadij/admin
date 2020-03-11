package org.genericConfig.admin.shared.user

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.03.2020
 */
case class UserDTO(
                    action : String = "",
                    params : Option[UserParamsDTO],
                    result : Option[UserResultDTO]
)

object UserDTO {
  implicit val format : Format[UserDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
      (JsPath \ "params").format(Format.optionWithNull[UserParamsDTO]) and
      (JsPath \ "result").format(Format.optionWithNull[UserResultDTO])
    )(UserDTO.apply, unlift(UserDTO.unapply))
}