/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonStepResult (
    stepId: String,
    status: String,
    message: String,
    visualProposalForAdditionalStepInOneLevel: Set[String],
    dependenciesForAdditionalStepsInOneLevel: Set[JsonDependencyForAdditionalStepsInOneLevel]
)

object JsonStepResult {
  implicit val format = Json.writes[JsonStepResult]
}