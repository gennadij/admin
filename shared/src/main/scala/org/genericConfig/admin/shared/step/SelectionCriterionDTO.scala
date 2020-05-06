package org.genericConfig.admin.shared.step

import play.api.libs.functional.syntax._
import play.api.libs.json.{Format, JsPath}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.04.2020
 */
case class SelectionCriterionDTO(
                                  min: Option[Int] = None,
                                  max: Option[Int] = None
                                )

object SelectionCriterionDTO{
  implicit val format: Format[SelectionCriterionDTO] = (
    (JsPath \ "min").format(Format.optionWithNull[Int]) and
    (JsPath \ "max").format(Format.optionWithNull[Int])
  )(SelectionCriterionDTO.apply, unlift(SelectionCriterionDTO.unapply))
}