package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocket
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.04.2020
 */
class Start {

  def actionGetUser() = {
    val getUser = Json.toJson(UserDTO(
      action = Actions.GET_USER,
      params = Some(UserParamsDTO(
        username = jQuery("#username").value().toString,
        password = jQuery("#password").value().toString,
        update = None,

      )),
      result = None
    )).toString
    println("OUT -> " + getUser)
    WebSocket.webSocket.send(getUser)
  }
}
