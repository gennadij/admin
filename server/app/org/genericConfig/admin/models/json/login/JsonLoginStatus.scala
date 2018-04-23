package org.genericConfig.admin.models.json.login

import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import org.genericConfig.admin.shared.json.status.JsonStatus

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 18.04.2018
 */
case class JsonLoginStatus (
    userLogin: Option[JsonStatus],
    common: Option[JsonStatus]
)

object JsonLoginStatus {
  implicit val writerJsonRegistrationStatus: Writes[JsonLoginStatus] = (
    (JsPath \ "userLogin").write(Writes.optionWithNull[JsonStatus]) and
    (JsPath \ "common").write(Writes.optionWithNull[JsonStatus])
  )(unlift(JsonLoginStatus.unapply))
}