package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigGraphComponentDTO (
                                   id : String,
                                   x : Int,
                                   y : Int
                                   )

object ConfigGraphComponentDTO {
  implicit val format: Format[ConfigGraphComponentDTO] = (
    (JsPath \ "id").format(Format.of[String]) and
    (JsPath \ "x").format(Format.of[Int]) and
    (JsPath \ "y").format(Format.of[Int])
  )(ConfigGraphComponentDTO.apply, unlift(ConfigGraphComponentDTO.unapply))
}
