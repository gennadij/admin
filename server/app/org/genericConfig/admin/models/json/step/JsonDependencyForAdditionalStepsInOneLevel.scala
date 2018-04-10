package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 15.08.2017
 */
case class JsonDependencyForAdditionalStepsInOneLevel (
    componentOutId: String,
    componentInId: String,
    dependencyId: String,
    dependencyType: String,
    visualization: String,
    nameToShow: String
)

object JsonDependencyForAdditionalStepsInOneLevel {
  implicit val format = Json.writes[JsonDependencyForAdditionalStepsInOneLevel]
}