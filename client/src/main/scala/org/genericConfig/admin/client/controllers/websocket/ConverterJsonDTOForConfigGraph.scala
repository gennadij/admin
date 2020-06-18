package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.models.ConfigGraph
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.configGraph.ConfigGraphDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 18.06.2020
 */
object ConverterJsonDTOForConfigGraph {
  def configGraph(receivedMessage: JsValue): Unit = {
    new ConverterJsonDTOForConfigGraph().configGraph(receivedMessage)
  }
}

class ConverterJsonDTOForConfigGraph {
  private def configGraph(receivedMessage: JsValue): Unit = {
    Json.fromJson[ConfigGraphDTO](receivedMessage) match {
      case configGraphDTO: JsSuccess[ConfigGraphDTO] => new ConfigGraph().showConfigGraph(configGraphDTO.get)
      case e: JsError => println("Errors -> " + Actions.CONFIG_GRAPH + ": " + JsError.toJson(e).toString())
    }
  }
}
