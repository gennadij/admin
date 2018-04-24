package org.genericConfig.admin.shared.json.login

import play.api.libs.functional.syntax._
import play.api.libs.json.Writes
import play.api.libs.json.JsPath
import org.genericConfig.admin.shared.json.status.JsonStatus
import play.api.libs.json.Format

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
  implicit val writerJsonRegistrationStatus: Format[JsonLoginStatus] = (
    (JsPath \ "userLogin").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
  )(JsonLoginStatus.apply, unlift(JsonLoginStatus.unapply))
}