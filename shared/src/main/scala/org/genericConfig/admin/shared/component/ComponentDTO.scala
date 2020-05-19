package org.genericConfig.admin.shared.component

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.05.2020
 */

case class ComponentDTO(
                       action : String,
                       params : Option[ComponentParamsDTO] = None,
                       result : Option[ComponentResultDTO] = None
                       )
object ComponentDTO{
  implicit val format: Format[ComponentDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
      (JsPath \ "params").format(Format.optionWithNull[ComponentParamsDTO]) and
      (JsPath \ "result").format(Format.optionWithNull[ComponentResultDTO])
    )(ComponentDTO.apply, unlift(ComponentDTO.unapply))
}