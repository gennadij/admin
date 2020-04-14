package org.genericConfig.admin.shared.step

import play.api.libs.json.{Json, OFormat}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 14.04.2020
 */
case class SelectionCriterionDTO(
                                  min: Int,
                                  max: Int
                                )

object SelectionCriterionDTO{
  implicit val format: OFormat[SelectionCriterionDTO] = Json.format[SelectionCriterionDTO]
}