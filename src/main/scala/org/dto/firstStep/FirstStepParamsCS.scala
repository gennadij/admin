package org.dto.firstStep

import play.api.libs.json.Json

case class FirstStepParamsCS (
  adminId: String,
  loginStatus: Boolean,
  kind: String
)


object FirstStepParamsCS {
  implicit val fortmat = Json.reads[FirstStepParamsCS]
}