package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 20.06.2020
 */
case class ConfigGraphD3LinkDTO (
                                source : ConfigGraphD3NodeDTO,
                                target : ConfigGraphD3NodeDTO
                                )

object ConfigGraphD3LinkDTO {
  implicit val format: Format[ConfigGraphD3LinkDTO] = (
    (JsPath \ "source").format(Format.of[ConfigGraphD3NodeDTO]) and
    (JsPath \ "target").format(Format.of[ConfigGraphD3NodeDTO])
  )(ConfigGraphD3LinkDTO.apply, unlift(ConfigGraphD3LinkDTO.unapply))
}