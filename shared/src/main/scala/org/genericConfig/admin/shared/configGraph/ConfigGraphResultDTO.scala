package org.genericConfig.admin.shared.configGraph

import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 06.06.2020
  */
case class ConfigGraphResultDTO (
                                steps : Option[List[ConfigGraphStepDTO]] = None,
                                components : Option[List[ConfigGraphComponentDTO]] = None,
                                edges : Option[List[ConfigGraphEdgeDTO]] = None,
                                errors : Option[List[ErrorDTO]] = None
                                )

object ConfigGraphResultDTO {
  implicit val format: Format[ConfigGraphResultDTO] = (
    (JsPath \ "steps").format(Format.optionWithNull[List[ConfigGraphStepDTO]]) and
    (JsPath \ "components").format(Format.optionWithNull[List[ConfigGraphComponentDTO]]) and
    (JsPath \ "edges").format(Format.optionWithNull[List[ConfigGraphEdgeDTO]]) and
    (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
  )(ConfigGraphResultDTO.apply, unlift(ConfigGraphResultDTO.unapply))
}
