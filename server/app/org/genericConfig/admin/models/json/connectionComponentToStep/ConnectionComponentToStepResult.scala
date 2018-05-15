package org.genericConfig.admin.models.json.connectionComponentToStep

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.01.2016
 */

case class ConnectionComponentToStepResult (
    status: String,
    message: String
//    TODO v016
//    ,
//    visualProposalForAdditionalStepInOneLevel: Set[String],
//    dependenciesForAdditionalStepsInOneLevel: Set[JsonDependencyForAdditionalStepsInOneLevel]
)

object ConnectionComponentToStepResult {
  implicit val format = Json.writes[ConnectionComponentToStepResult]
}