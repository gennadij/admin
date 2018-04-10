package org.genericConfig.admin.models.json.step

import org.genericConfig.admin.models.json.DTOIds
import org.genericConfig.admin.models.json.DTONames
import play.api.libs.json.Json

/**
 *  Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 */

case class JsonStepIn (
    dtoId: Int = DTOIds.CREATE_STEP,
    dto: String = DTONames.CREATE_STEP,
    params: JsonStepParams
)


object JsonStepIn {
  implicit val format = Json.reads[JsonStepIn]
}