/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.step

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class StepParams (
    componentId: String,
    nameToShow: String,
    kind: String,
    selectionCriterium: SelectionCriterium
)


object StepParams {
  implicit val format = Json.reads[StepParams]
}
