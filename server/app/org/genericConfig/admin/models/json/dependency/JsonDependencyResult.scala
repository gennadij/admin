package org.genericConfig.admin.models.json.dependency

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2017
 */

case class JsonDependencyResult (
    componentFromId: String,
    componentToId: String,
    dependencyId: String,
    dependencyType: String,
    visualization: String,
    nameToShow: String,
    status: String,
    message: String
)

object JsonDependencyResult {
  implicit val format = Json.writes[JsonDependencyResult]
}