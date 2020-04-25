package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.html.HtmlElementIds
import org.genericConfig.admin.client.views.user.{UpdateUserPage, UserPage}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.config.{ConfigDTO, ConfigParamsDTO}
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO, UserUpdateDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2020
  */

class User {
  def showUser(param: Option[Any]): Unit = {
//    new UserPage().drawUserPage(param.get.asInstanceOf[UserDTO])
    new UserPage().drawUserWithConfigPage(userDTO = Some(param.get.asInstanceOf[UserDTO]))
  }

  def showUpdateUserPage(param : Option[Any]) : Unit = {
    new UpdateUserPage().drawUpdateUserPage(param.get.asInstanceOf[UserDTO])
  }


  def updateUsername(param : Option[Any]): Unit = {
    val updateUsername = Json.toJson(
      UserDTO(
        action = Actions.UPDATE_USER,
        params = Some(UserParamsDTO(
          username = "",
          password = "",
          update = Some(UserUpdateDTO(
            oldUsername = param.get.asInstanceOf[UserDTO].result.get.username.get,
            newUsername = jQuery(HtmlElementIds.inputFieldUpdateUsernameJQuery).value().toString,
            oldPassword = "",
            newPassword = ""
          )),
        )),
        result = None
      )
    ).toString
    println("OUT -> " + updateUsername)
    WebSocketListner.webSocket.send(updateUsername)
  }

  def deleteUser(param : Option[Any]): Unit = {
    val deleteUsername = Json.toJson(
      UserDTO(
        action = Actions.DELETE_USER,
        params = Some(UserParamsDTO(
          username = param.get.asInstanceOf[UserDTO].result.get.username.get,
          password = "",
          update = None,
        )),
        result = None
      )
    ).toString
    println("OUT -> " + deleteUsername)
    WebSocketListner.webSocket.send(deleteUsername)
  }
}
