package org.genericConfig.admin.shared.user.json

import play.api.libs.json.JsPath
import play.api.libs.functional.syntax._
import play.api.libs.json.Format
import org.genericConfig.admin.shared.common.json.JsonStatus

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 11.04.2018
 */
case class JsonUserStatus (
    addUser: Option[JsonStatus] = None,
    deleteUser: Option[JsonStatus] = None,
    getUser: Option[JsonStatus] = None,
    updateUser: Option[JsonStatus] = None,
    common: Option[JsonStatus] = None
)

object JsonUserStatus {
  implicit val format: Format[JsonUserStatus] = (
    (JsPath \ "addUser").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "deleteUser").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "getUser").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "updateUser").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
  )(JsonUserStatus.apply, unlift(JsonUserStatus.unapply))
}