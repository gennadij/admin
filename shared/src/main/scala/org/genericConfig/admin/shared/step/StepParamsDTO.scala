package org.genericConfig.admin.shared.step

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.04.2020
 */
case class StepParamsDTO(
                          nameToShow: Option[String] = None,
                          stepId: Option[String] = None,
                          fromId: Option[String] = None,
                          kind: Option[String] = None,
                          selectionCriterion: Option[SelectionCriterionDTO] = None
                        )

object StepParamsDTO{
  implicit val format: Format[StepParamsDTO] = (
    (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
      (JsPath \ "stepId").format(Format.optionWithNull[String]) and
      (JsPath \ "fromId").format(Format.optionWithNull[String]) and
      (JsPath \ "kind").format(Format.optionWithNull[String]) and
      (JsPath \ "selectionCriterion").format(Format.optionWithNull[SelectionCriterionDTO])
    )(StepParamsDTO.apply, unlift(StepParamsDTO.unapply))
}
