package org.dto.component

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 1.1.2017
 */
case class ComponentParams (
    stepId: String,
    kind: String
)

object ComponentParams {
  implicit val format = Json.reads[ComponentParams]
}