/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.genericConfig.admin.models.json.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonStepParams (
    componentId: String,
    nameToShow: String,
    kind: String,
    selectionCriterium: JsonSelectionCriterium
)

object JsonStepParams {
  implicit val format = Json.reads[JsonStepParams]
}

