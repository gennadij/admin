package org.genericConfig.admin.models.json.component

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 */

case class ComponentResult (
    componentId: String
    ,status: String
    ,message: String
)

object ComponentResult {
  implicit val format = Json.writes[ComponentResult]
}