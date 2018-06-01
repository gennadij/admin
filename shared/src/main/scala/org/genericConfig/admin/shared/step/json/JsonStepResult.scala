package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonStatus
import play.api.libs.functional.syntax._
import play.api.libs.json.Format
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonStepResult (
    stepId: Option[String],
    visualProposalForAdditionalStepInOneLevel: Set[String],
    dependenciesForAdditionalStepsInOneLevel: Set[JsonDependencyForAdditionalStepsInOneLevel],
    status: JsonStepStatus
)

object JsonStepResult {
  implicit val format: Format[JsonStepResult] = (
    (JsPath \ "stepId").format(Format.optionWithNull[String]) and
    (JsPath \ "visualProposalForAdditionalStepInOneLevel").format(Format.of[Set[String]]) and
    (JsPath \ "dependenciesForAdditionalStepsInOneLevel").format(Format.of[Set[JsonDependencyForAdditionalStepsInOneLevel]]) and
    (JsPath \ "status").format(Format.of[JsonStepStatus])
  )(JsonStepResult.apply, unlift(JsonStepResult.unapply))
}