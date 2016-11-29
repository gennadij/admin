package org.dto

import play.api.libs.json.Json

case class ConfigTreeParamsCS (
    adminId: String,
    loginStatus: String
)

object ConfigTreeParamsCS {
  implicit val format = Json.reads[ConfigTreeParamsCS]
}