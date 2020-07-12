package org.genericConfig.admin.shared.step

import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.05.2020
 */
case class StepPropertiesDTO (
                             nameToShow : Option[String] = None,
                             nickName : Option[String] = None,
                             selectionCriterion: Option[SelectionCriterionDTO] = None
                             )

object StepPropertiesDTO {
  implicit val format: Format[StepPropertiesDTO] = (
    (JsPath \ "nameToShow").format(Format.optionWithNull[String]) and
    (JsPath \ "nickName").format(Format.optionWithNull[String]) and
    (JsPath \ "selectionCriterion").format(Format.optionWithNull[SelectionCriterionDTO])
  )(StepPropertiesDTO.apply, unlift(StepPropertiesDTO.unapply))
}
