package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO}
import play.api.libs.json.Json

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.04.2020
 */
class Start {

  def start(): Unit = {
    new StartPage().drawStartPage(None)
  }

  def actionGetUser(): Unit = {
    val getUser = Json.toJson(UserDTO(
      action = Actions.GET_USER,
      params = Some(UserParamsDTO(
        username = "configGraph_V0.1", //jQuery("#username").value().toString,
        password = "configGraph_V0.1", //jQuery("#password").value().toString,
        update = None,

      )),
      result = None
    )).toString
    println("OUT -> " + getUser)
    WebSocketListner.webSocket.send(getUser)
  }

//  def showUserPage(param : Option[Any]): Unit = {
//    new UserPage().drawUserPage(param.get.asInstanceOf[UserDTO])
//  }
}
