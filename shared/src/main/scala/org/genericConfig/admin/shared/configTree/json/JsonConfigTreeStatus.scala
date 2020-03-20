package org.genericConfig.admin.shared.configTree.json

import play.api.libs.functional.syntax._
import org.genericConfig.admin.shared.common.json.JsonStatus
import play.api.libs.json.Format
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.04.2018
 */
case class JsonConfigTreeStatus (
    getConfigTree: Option[JsonStatus], 
    common: Option[JsonStatus]
)

object JsonConfigTreeStatus {
  implicit val writerJsonConfigTreeStatus: Format[JsonConfigTreeStatus] = (
    (JsPath \ "getConfigTree").format(Format.optionWithNull[JsonStatus]) and
    (JsPath \ "common").format(Format.optionWithNull[JsonStatus])
  )(JsonConfigTreeStatus.apply, unlift(JsonConfigTreeStatus.unapply))
}