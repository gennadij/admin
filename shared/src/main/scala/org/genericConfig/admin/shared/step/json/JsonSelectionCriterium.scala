package org.genericConfig.admin.shared.step.json

import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 09.01.2016
 */
case class JsonSelectionCriterium (
    min: Int,
    max: Int
)

object JsonSelectionCriterium {
  implicit val format = Json.format[JsonSelectionCriterium]
}