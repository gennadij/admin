package org.dto.step

import play.api.libs.json.Json

case class SelectionCriterium (
    min: Int,
    max: Int
)

object SelectionCriterium {
  implicit val format = Json.format[SelectionCriterium]
}