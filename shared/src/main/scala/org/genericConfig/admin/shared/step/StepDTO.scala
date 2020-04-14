package org.genericConfig.admin.shared.step

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class StepDTO(
                 action : String,
                 params : Option[StepParamsDTO] = None,
                 result : Option[StepResultDTO] = None
                 )

object StepDTO{
  implicit val format: Format[StepDTO] = (
    (JsPath \ "stepId").format(Format.of[String]) and
      (JsPath \ "params").format(Format.optionWithNull[StepParamsDTO]) and
      (JsPath \ "result").format(Format.optionWithNull[StepResultDTO])
    )(StepDTO.apply, unlift(StepDTO.unapply))
}
