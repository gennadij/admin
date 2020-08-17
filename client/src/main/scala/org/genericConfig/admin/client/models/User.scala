package org.genericConfig.admin.client.models

import org.genericConfig.admin.client.controllers.websocket.WebSocketListner
import org.genericConfig.admin.client.views.StartPage
import org.genericConfig.admin.client.views.html.{HtmlElementIds, HtmlElementText}
import org.genericConfig.admin.client.views.user.{AddUserPage, UpdateUserPage, UserPage}
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.{UserDTO, UserParamsDTO, UserUpdateDTO}
import org.scalajs.jquery.jQuery
import play.api.libs.json.Json

/**
  * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
  *
  * Created by Gennadi Heimann 10.04.2020
  */

class User {
  def getUserRequest(): Unit = {
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
    WebSocketListner.webSocket.send(getUser)
  }

  def getUserResponse(param: Option[Any]): Unit = {
    new UserPage().drawUserPageWithConfigPage(userDTO = Some(param.get.asInstanceOf[UserDTO]))
  }

  def addUserRequest(): Unit = {
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

  def addUserResponse(param: Option[Any]): Unit = {
    HtmlElementText.drawAlert("User : " + param.get.asInstanceOf[UserDTO].result.head.username.get + " wurde regestriert")
    new StartPage().drawStartPage(None)
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
