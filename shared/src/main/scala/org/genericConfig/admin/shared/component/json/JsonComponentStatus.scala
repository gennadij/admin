package org.genericConfig.admin.shared.component.json

import org.genericConfig.admin.shared.common.json.JsonStatus
import play.api.libs.functional.syntax._
import play.api.libs.json.Format
import play.api.libs.json.JsPath
/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 14.06.2018
  */
case class JsonComponentStatus (
    addComponent: Option[JsonStatus],
    appendComponent: Option[JsonStatus],
    deleteComponent: Option[JsonStatus],
    updateComponent: Option[JsonStatus],
    common: Option[JsonStatus]
)

object JsonComponentStatus {
  implicit val format: Format[JsonComponentStatus] = (
    (JsPath \ "addComponent").format(Format.optionWithNull[JsonStatus]) and
      (JsPath \ "appendComponent").format(Format.optionWithNull[JsonStatus]) and
      (JsPath \ "deleteComponent").format(Format.optionWithNull[JsonStatus]) and
      (JsPath \ "updateComponent").format(Format.optionWithNull[JsonStatus]) and
      (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
    )(JsonComponentStatus.apply, unlift(JsonComponentStatus.unapply))
}