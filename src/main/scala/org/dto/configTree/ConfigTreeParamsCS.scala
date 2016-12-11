package org.dto.configTree

import play.api.libs.json.Json

case class ConfigTreeParamsCS (
    adminId: String,
    loginStatus: Boolean
)

object ConfigTreeParamsCS {
  implicit val format = Json.reads[ConfigTreeParamsCS]
}