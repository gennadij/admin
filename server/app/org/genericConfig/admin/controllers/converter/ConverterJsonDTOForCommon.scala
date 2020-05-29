package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.error.{ErrorParamsDTO, ErrorrDTO}
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
      Actions.ERROR,
      ErrorParamsDTO(
        "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
      )
    )
    Logger.error(error.toString)
    Json.toJson(error)
  }
}
