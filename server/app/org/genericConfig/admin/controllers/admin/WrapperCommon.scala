package org.genericConfig.admin.controllers.admin

import org.genericConfig.admin.shared.common.json.JsonNames
import org.genericConfig.admin.shared.error.json.{JsonErrorIn, JsonErrorParams}
import play.api.Logger
import play.api.libs.json.{JsError, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 31.03.2020
 */
trait WrapperCommon {
  def jsonError(errorText: String, e: JsError): JsValue = {
    val error = JsonErrorIn(
      JsonNames.ERROR,
      JsonErrorParams(
        "Errors -> " + errorText + " : " + JsError.toJson(e).toString()
      )
    )
    Logger.error(error.toString)
    Json.toJson(error)
  }
}
