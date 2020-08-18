package org.genericConfig.admin.shared.dependency

import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.08.2020
 */
case class DependencyDTO (
  action : String,
  params : Option[DependencyParamsDTO] = None,
  result : Option[DependencyResultDTO] = None
)

object DependencyDTO {
  implicit val format: Format[DependencyDTO] = (
    (JsPath \ "action").format(Format.of[String]) and
    (JsPath \ "params").format(Format.optionWithNull[DependencyParamsDTO]) and
    (JsPath \ "result").format(Format.optionWithNull[DependencyResultDTO])
  )(DependencyDTO.apply, unlift(DependencyDTO.unapply))
}
