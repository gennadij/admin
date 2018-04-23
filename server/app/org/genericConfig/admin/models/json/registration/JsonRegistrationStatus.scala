//package org.genericConfig.admin.models.json.registration
//
//import org.genericConfig.admin.models.json.common.JsonStatus
//import play.api.libs.json.JsPath
//import play.api.libs.json.Writes
//import play.api.libs.functional.syntax._
///**
// * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
// * 
// * Created by Gennadi Heimann 11.04.2018
// */
//case class JsonRegistrationStatus (
//    addUser: Option[JsonStatus],
//    common: Option[JsonStatus]
//)
//
//object JsonRegistrationStatus {
//  import org.genericConfig.admin.models.json.common.JsonStatus.writerJsonStatus
//  implicit val writerJsonRegistrationStatus: Writes[JsonRegistrationStatus] = (
//    (JsPath \ "addUser").write(Writes.optionWithNull[JsonStatus]) and
//    (JsPath \ "common").write(Writes.optionWithNull[JsonStatus])
//  )(unlift(JsonRegistrationStatus.unapply))
//}