package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.old.registration.RegistrationPage
import org.genericConfig.admin.client.old.user.UserPage
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.UserDTO
import play.api.libs.json.{JsError, JsSuccess, JsValue, Json}
import org.scalajs.dom.WebSocket

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 08.04.2020
 */

object ConverterFromJsonForUser {
  def addUser(receivedMessage: JsValue, webSocket: WebSocket): Unit = {
    new ConverterFromJsonForUser(webSocket).addUser(receivedMessage)
  }
  def getUser(receivedMessage: JsValue, webSocket: WebSocket): Unit = {
    new ConverterFromJsonForUser(webSocket).getUser(receivedMessage)
  }

  def deleteUser(receivedMessage: JsValue, webSocket: WebSocket): Unit = {
    ???
  }

  def updateUser(receivedMessage: JsValue, webSocket: WebSocket): Unit = {
    ???
  }
}


class ConverterFromJsonForUser(webSocket : WebSocket) {
  private def addUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case addUserResult: JsSuccess[UserDTO] =>
        new RegistrationPage().drawRegistrationPage(addUserResult.get.result.get.errors)
      case e: JsError => println("Errors -> " + Actions.ADD_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def getUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case getUserResult: JsSuccess[UserDTO] => new UserPage(webSocket).drawUserPage(getUserResult.get)
      case e: JsError => println("Errors -> " + Actions.GET_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def deleteUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case deleteUserResult: JsSuccess[UserDTO] => ???
      case e: JsError => println("Errors -> " + Actions.DELETE_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def updateUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case updateUserResult: JsSuccess[UserDTO] => ???
      case e: JsError => println("Errors -> " + Actions.DELETE_USER + ": " + JsError.toJson(e).toString())
    }
  }
}


