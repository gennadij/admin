package org.genericConfig.admin.shared.step.json

import play.api.libs.functional.syntax._
import org.genericConfig.admin.shared.common.json.JsonStatus
import play.api.libs.json.Format
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 14.05.2018
 */
case class JsonStepStatus (
    addStep: Option[JsonStatus],
    deleteStep: Option[JsonStatus],
    updateStep: Option[JsonStatus],
    appendStep: Option[JsonStatus],
    common: Option[JsonStatus]
)

object JsonStepStatus {
  implicit val format: Format[JsonStepStatus] = (
    (JsPath \ "addStep").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "deleteStep").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "updateStep").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "appendStep").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
  )(JsonStepStatus.apply, unlift(JsonStepStatus.unapply))
}