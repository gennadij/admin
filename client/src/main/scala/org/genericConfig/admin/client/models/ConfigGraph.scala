package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.configGraph.ConfigGraphPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.UserConfigDTO
import org.genericConfig.admin.shared.configGraph.{ConfigGraphDTO, ConfigGraphParamsDTO}
import org.scalajs.dom.window.screen
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 17.06.2020
 */
class ConfigGraph {
  def configGraph(param: Option[Any]) = {
    val configId : String = param.get.asInstanceOf[UserConfigDTO].configId.get

    //TODO dynamische SVG Anpassung damit gesamten ConfigGraph gut sichtbar ist
    val height = screen.height
    val width = screen.width

    val configGraphRequest = Json.toJson(
        ConfigGraphDTO(
          action = Actions.CONFIG_GRAPH,
          params = Some(ConfigGraphParamsDTO(
            configId = configId,
            screenWidth = width.toInt,
            screenHeight = height.toInt
        ))
    )
    ).toString
    println("OUT -> " + configGraphRequest)
    WebSocketListner.webSocket.send(configGraphRequest)
  }

  def showConfigGraph(configGraphDTO: ConfigGraphDTO): Unit = {
    new ConfigGraphPage().drawConfigGraph(configGraphDTO.result.get)
  }
}
