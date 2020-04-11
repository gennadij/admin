package org.genericConfig.admin.client.controllers.websocket

import org.genericConfig.admin.client.models.{Start, User}
import org.genericConfig.admin.client.views.user.RegistrationPage
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
  def addUser(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForUser().addUser(receivedMessage)
  }
  def getUser(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForUser().getUser(receivedMessage)
  }

  def deleteUser(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForUser().deleteUser(receivedMessage)
  }

  def updateUser(receivedMessage: JsValue): Unit = {
    new ConverterFromJsonForUser().updateUser(receivedMessage)
  }
}


class ConverterFromJsonForUser() {
  private def addUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case addUserResult: JsSuccess[UserDTO] =>
        new RegistrationPage().drawRegistrationPage(addUserResult.get.result.get.errors)
      case e: JsError => println("Errors -> " + Actions.ADD_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def getUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case getUserResult: JsSuccess[UserDTO] => new User().showUser(Some(getUserResult.value))
      case e: JsError => println("Errors -> " + Actions.GET_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def deleteUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case deleteUserResult: JsSuccess[UserDTO] => new Start().start()
      case e: JsError => println("Errors -> " + Actions.DELETE_USER + ": " + JsError.toJson(e).toString())
    }
  }

  private def updateUser(receivedMessage: JsValue): Unit = {
    Json.fromJson[UserDTO](receivedMessage) match {
      case updateUserResult: JsSuccess[UserDTO] => new User().showUser(Some(updateUserResult.value))
      case e: JsError => println("Errors -> " + Actions.DELETE_USER + ": " + JsError.toJson(e).toString())
    }
  }
}


