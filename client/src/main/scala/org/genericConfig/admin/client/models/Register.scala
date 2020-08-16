package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.user.AddUserPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2020
  */

class Register {
  def showAddUser(): Unit = {
    new AddUserPage().drawAddUserPage(None)
  }

  def actionAddUser(): Unit = {
    val addUser = Json.toJson(
      UserDTO(
        action = Actions.ADD_USER,
        params = Some(UserParamsDTO(
          username = jQuery("#username").value().toString,
          password = jQuery("#password").value().toString,
          update = None,
        )),
        result = None
      )
    ).toString
    println("OUT -> " + addUser)
    WebSocketListner.webSocket.send(addUser)
  }
}
