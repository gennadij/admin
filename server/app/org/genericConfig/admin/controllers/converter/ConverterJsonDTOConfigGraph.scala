package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.logic.ConfigGraph
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 10.06.2020
 */
trait ConverterJsonDTOConfigGraph extends ConverterJsonDTOForCommon{
  private[converter] def configGraph(receivedMessage: JsValue): JsValue = {
    Json.fromJson[ConfigGraphDTO](receivedMessage) match {
      case configGraph: JsSuccess[ConfigGraphDTO] => Json.toJson(ConfigGraph.configGraph(configGraph.get))
      case e : JsError => jsonError(Actions.CONFIG_GRAPH, e)
    }
  }
}
