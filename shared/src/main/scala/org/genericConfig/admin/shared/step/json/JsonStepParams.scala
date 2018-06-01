package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonStepParams (
    configId: String,
    componentId: String,
    stepId: String,
    nameToShow: String,
    kind: String,
    selectionCriterium: JsonSelectionCriterium
)

object JsonStepParams {
  implicit val format = Json.format[JsonStepParams]
}

