package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 20.06.2020
 */
case class ConfigGraphD3NodeDTO (
                                  id : String,
                                  x : Int,
                                  y : Int
                                )

object ConfigGraphD3NodeDTO {
  implicit val format: Format[ConfigGraphD3NodeDTO] = (
    (JsPath \ "id").format(Format.of[String]) and
    (JsPath \ "x").format(Format.of[Int]) and
    (JsPath \ "y").format(Format.of[Int])
    )(ConfigGraphD3NodeDTO.apply, unlift(ConfigGraphD3NodeDTO.unapply))
}