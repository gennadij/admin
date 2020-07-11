package org.genericConfig.admin.shared.configGraph

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath, Json}

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 06.06.2020
  */
case class ConfigGraphParamsDTO (
                                  configId : String,
                                  screenWidth : Int,
                                  screenHeight : Int
                                )

object ConfigGraphParamsDTO {
  implicit val format: Format[ConfigGraphParamsDTO] = Json.format[ConfigGraphParamsDTO]
}
