package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigGraphEdgeDTO (
                              source : String,
                              target : String
                              )

object ConfigGraphEdgeDTO {
  implicit val format: Format[ConfigGraphEdgeDTO] = (
    (JsPath \ "source").format(Format.of[String]) and
    (JsPath \ "target").format(Format.of[String])
  )(ConfigGraphEdgeDTO.apply, unlift(ConfigGraphEdgeDTO.unapply))
}
