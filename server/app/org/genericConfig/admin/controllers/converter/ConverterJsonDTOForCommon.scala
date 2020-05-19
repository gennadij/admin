package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.error.{ErrorParamsDTO, ErrorrDTO}
import org.genericConfig.admin.shared.error.json.ErrorParamsDTO
import play.api.Logger
import play.api.libs.json.{JsError, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 31.03.2020
 */
trait ConverterJsonDTOForCommon {
  def jsonError(errorText: String, e: JsError): JsValue = {
    val error = ErrorrDTO(
      JsonNames.ERROR,
      ErrorParamsDTO(
        "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
      )
    )
    Logger.error(error.toString)
    Json.toJson(error)
  }
}
