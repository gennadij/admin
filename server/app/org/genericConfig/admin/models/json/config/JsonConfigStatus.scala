package org.genericConfig.admin.models.json.config

import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import org.genericConfig.admin.shared.json.status.JsonStatus

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 19.04.2018
 */
case class JsonConfigStatus (
    addConfig: Option[JsonStatus],
    common: Option[JsonStatus]
)

object JsonConfigStatus {
  import org.genericConfig.admin.shared.json.status.JsonStatus.writerJsonStatus
  implicit val writerJsonRegistrationStatus: Writes[JsonConfigStatus] = (
    (JsPath \ "addConfig").write(Writes.optionWithNull[JsonStatus]) and
    (JsPath \ "common").write(Writes.optionWithNull[JsonStatus])
  )(unlift(JsonConfigStatus.unapply))
}