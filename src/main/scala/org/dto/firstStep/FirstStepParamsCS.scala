package org.dto.firstStep

import play.api.libs.json.Json

case class FirstStepParamsCS (
  adminId: String,
  kind: String,
  loginStatus: Boolean
)


object FirstStepParamsCS {
  implicit val fortmat = Json.reads[FirstStepParamsCS]
}