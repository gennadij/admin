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
                          stepId: Option[String] = None,
                          outId: Option[String] = None, //configId or componentId
                          kind: Option[String] = None,
                          properties : Option[StepPropertiesDTO] = None
                        )

object StepParamsDTO{
  implicit val format: Format[StepParamsDTO] = (
      (JsPath \ "stepId").format(Format.optionWithNull[String]) and
      (JsPath \ "outId").format(Format.optionWithNull[String]) and
      (JsPath \ "kind").format(Format.optionWithNull[String]) and
      (JsPath \ "properties").format(Format.optionWithNull[StepPropertiesDTO])
    )(StepParamsDTO.apply, unlift(StepParamsDTO.unapply))
}
