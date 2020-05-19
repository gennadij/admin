package org.genericConfig.admin.shared.step

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */

case class StepDTO(
                 action : String,
                 params : Option[StepParamsDTO] = None,
                 result : Option[StepResultDTO] = None
                 )

object StepDTO{
  implicit val format: Format[StepDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
      (JsPath \ "params").format(Format.optionWithNull[StepParamsDTO]) and
      (JsPath \ "result").format(Format.optionWithNull[StepResultDTO])
    )(StepDTO.apply, unlift(StepDTO.unapply))
}
