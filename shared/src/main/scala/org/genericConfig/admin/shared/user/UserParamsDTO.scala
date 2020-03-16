package org.genericConfig.admin.shared.user

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.03.2020
 */
case class UserParamsDTO(
                        username : String,
                        password : String,
                        update : Option[UserUpdateDTO]
)

object UserParamsDTO {
  implicit val format: Format[UserParamsDTO] = (
      (JsPath \ "username").format(Format.of[String]) and
      (JsPath \ "password").format(Format.of[String]) and
      (JsPath \ "update").format(Format.optionWithNull[UserUpdateDTO])
    )(UserParamsDTO.apply, unlift(UserParamsDTO.unapply))
}