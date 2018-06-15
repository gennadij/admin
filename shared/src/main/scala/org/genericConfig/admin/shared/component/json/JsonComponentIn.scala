package org.genericConfig.admin.shared.component.json

import play.api.libs.json.Json
import org.genericConfig.admin.shared.common.json.JsonNames

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.12.2016
 * 
 * {jsonId : 8, dto : Component, params : {adminId : #40:0, kind : immutable}
 */
case class JsonComponentIn (
    json: String,
    params: JsonComponentParams
)

object JsonComponentIn {
    implicit val format = Json.format[JsonComponentIn]
}