package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigGraphD3PropertiesDTO (
                                      svgHeight : Int,
                                      svgWidth : Int
                                      )

object ConfigGraphD3PropertiesDTO{
  implicit val format : Format[ConfigGraphD3PropertiesDTO] = (
    (JsPath \ "svgHeight").format(Format.of[Int]) and
    (JsPath \ "svgWidth").format(Format.of[Int])
  )(ConfigGraphD3PropertiesDTO.apply, unlift(ConfigGraphD3PropertiesDTO.unapply))
}