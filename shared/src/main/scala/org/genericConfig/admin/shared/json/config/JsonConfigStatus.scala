package org.genericConfig.admin.shared.json.config

import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import play.api.libs.json.Format
import org.genericConfig.admin.shared.json.common.JsonStatus

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
case class JsonConfigStatus (
    addConfig: Option[JsonStatus],
    getConfigs: Option[JsonStatus],
    deleteConfig: Option[JsonStatus],
    updateConfig: Option[JsonStatus],
    common: Option[JsonStatus]
)

object JsonConfigStatus {
  implicit val writerJsonRegistrationStatus: Format[JsonConfigStatus] = (
    (JsPath \ "addConfig").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "getConfigs").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "deleteConfig").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "updateConfig").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
  )(JsonConfigStatus.apply, unlift(JsonConfigStatus.unapply))
}