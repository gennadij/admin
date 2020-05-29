package org.genericConfig.admin.shared.configTree.json

import org.genericConfig.admin.shared.component.ComponentDTO
import play.api.libs.functional.syntax._
import play.api.libs.json.Format
import play.api.libs.json.JsPath

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 * 
 * Created by Gennadi Heimann 27.04.2018
 */
case class JsonConfigTreeStatus (
    getConfigTree: Option[ComponentDTO],
    common: Option[ComponentDTO]
)

object JsonConfigTreeStatus {
  implicit val writerJsonConfigTreeStatus: Format[JsonConfigTreeStatus] = (
    (JsPath \ "getConfigTree").format(Format.optionWithNull[ComponentDTO]) and
    (JsPath \ "common").format(Format.optionWithNull[ComponentDTO])
  )(JsonConfigTreeStatus.apply, unlift(JsonConfigTreeStatus.unapply))
}