package org.genericConfig.admin.shared.user


import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, JsPath}

case class UserResultDTO(
                     userId: Option[String],
                     username : Option[String],
                     errors : Option[List[ErrorDTO]]
                     )

object UserResultDTO {
  implicit val format: Format[UserResultDTO] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
      (JsPath \ "username").format(Format.optionWithNull[String]) and
      (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
    )(UserResultDTO.apply, unlift(UserResultDTO.unapply))
}