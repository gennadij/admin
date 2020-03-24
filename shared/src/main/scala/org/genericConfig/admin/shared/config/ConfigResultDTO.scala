package org.genericConfig.admin.shared.config

import org.genericConfig.admin.shared.common.ErrorDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}

case class ConfigResultDTO(
                            userId: Option[String] = None,
                            configs : Option[List[UserConfigDTO]],
                            errors : Option[List[ErrorDTO]]
                          )

object ConfigResultDTO {
  implicit val format: Format[ConfigResultDTO] = (
    (JsPath \ "userId").format(Format.optionWithNull[String]) and
    (JsPath \ "configs").format(Format.optionWithNull[List[UserConfigDTO]]) and
    (JsPath \ "errors").format(Format.optionWithNull[List[ErrorDTO]])
  )(ConfigResultDTO.apply, unlift(ConfigResultDTO.unapply))
}