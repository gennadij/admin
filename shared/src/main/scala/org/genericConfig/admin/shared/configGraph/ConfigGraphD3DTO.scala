package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 20.06.2020
 */
case class ConfigGraphD3GraphDTO (
                                 nodes : List[ConfigGraphD3NodeDTO],
                                 links : List[ConfigGraphD3LinkDTO]
                                 )

object ConfigGraphD3GraphDTO {
  implicit val format: Format[ConfigGraphD3GraphDTO] = (
    (JsPath \ "nodes").format(Format.of[List[ConfigGraphD3NodeDTO]]) and
    (JsPath \ "links").format(Format.of[List[ConfigGraphD3LinkDTO]])
  (ConfigGraphD3GraphDTO.apply, unlift(ConfigGraphD3GraphDTO.unapply))
}
