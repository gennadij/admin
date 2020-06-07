package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigGraphStepDTO (
                              id : String,
                              x : Int,
                              y : Int
                              )

object ConfigGraphStepDTO {
  implicit val format: Format[ConfigGraphStepDTO] = (
    (JsPath \ "id").format(Format.of[String]) and
    (JsPath \ "x").format(Format.of[Int]) and
    (JsPath \ "y").format(Format.of[Int])
  )(ConfigGraphStepDTO.apply, unlift(ConfigGraphStepDTO.unapply))
}
