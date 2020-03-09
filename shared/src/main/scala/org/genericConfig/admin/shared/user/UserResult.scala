package org.genericConfig.admin.shared.user


import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, JsPath}

case class UserResult(
                     userId: Option[String],
                     username : Option[String],
                     errors : Option[List[Error]]
                     )

object UserResult {
  implicit val format: Format[UserResult] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
      (JsPath \ "username").format(Format.optionWithNull[String]) and
      (JsPath \ "errors").format(Format.optionWithNull[List[Error]])
    )(UserResult.apply, unlift(UserResult.unapply))
}