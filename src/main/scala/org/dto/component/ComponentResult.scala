/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 */
package org.dto.component

import play.api.libs.json.Json

/**
 * Created by Gennadi Heimann 19.12.2016
 * 
 * result : {componentId : #13:1, status : true, message : Nachricht}
 */

case class ComponentResult (
    componentId: String
    ,status: Boolean
    ,message: String
)

object ComponentResult {
  implicit val format = Json.writes[ComponentResult]
}