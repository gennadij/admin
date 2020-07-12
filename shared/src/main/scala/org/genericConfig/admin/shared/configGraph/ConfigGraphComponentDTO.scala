package org.genericConfig.admin.shared.configGraph

import org.genericConfig.admin.shared.component.ComponentUserPropertiesDTO
import play.api.libs.functional.syntax.{unlift, _}
import play.api.libs.json.{Format, JsPath}
/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 06.06.2020
 */
case class ConfigGraphComponentDTO (
                                   componentId : String,
                                   properties : ComponentUserPropertiesDTO
                                   )

object ConfigGraphComponentDTO {
  implicit val format: Format[ConfigGraphComponentDTO] = (
    (JsPath \ "id").format(Format.of[String]) and
    (JsPath \ "nameToShow").format(Format.of[ComponentUserPropertiesDTO])
  )(ConfigGraphComponentDTO.apply, unlift(ConfigGraphComponentDTO.unapply))
}
