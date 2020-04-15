package org.genericConfig.admin.shared.step

import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax.unlift
import play.api.libs.json.{Format, JsPath}
import play.api.libs.functional.syntax._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.04.2020
 */
case class StepResultDTO(
                          stepId: Option[String] = None,
                          errors: Option[List[ErrorDTO]]
                        )

object StepResultDTO{
  implicit val format: Format[StepResultDTO] = (
    (JsPath \ "stepId").format(Format.optionWithNull[String]) and
      (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
    )(StepResultDTO.apply, unlift(StepResultDTO.unapply))
}
