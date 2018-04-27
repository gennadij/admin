package org.genericConfig.admin.shared.login.json

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath
import play.api.libs.json.Format
import org.genericConfig.admin.shared.common.json.JsonStatus

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