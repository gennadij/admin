package org.genericConfig.admin.shared.component.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class JsonComponentParams (
    stepId: String,
    nameToShow: String,
    kind: String
)

object JsonComponentParams {
  implicit val format = Json.reads[JsonComponentParams]
}