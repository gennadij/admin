package org.genericConfig.admin.shared.configGraph

import org.genericConfig.admin.shared.step.StepPropertiesDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigGraphStepDTO (
                              stepId : String,
                              properties : StepPropertiesDTO
                              )

object ConfigGraphStepDTO {
  implicit val format: Format[ConfigGraphStepDTO] = (
    (JsPath \ "id").format(Format.of[String]) and
    (JsPath \ "nameToShow").format(Format.of[StepPropertiesDTO])
  )(ConfigGraphStepDTO.apply, unlift(ConfigGraphStepDTO.unapply))
}
