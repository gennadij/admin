package org.genericConfig.admin.controllers.converter

import org.genericConfig.admin.models.logic.User
import org.genericConfig.admin.shared.Actions
import org.genericConfig.admin.shared.user.UserDTO
import play.api.libs.json._

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 31.03.2020
 */

trait ConverterJsonDTOForUser extends ConverterJsonDTOForCommon {
  private[converter]def addUser(receivedMessage: JsValue): JsValue = {
    val addUser: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    addUser match {
      case s : JsSuccess[UserDTO] => Json.toJson(User.addUser(s.value))
      case e : JsError => jsonError(Actions.ADD_USER, e)
    }
  }

  private[converter] def deleteUser(receivedMessage: JsValue): JsValue = {
    val deleteUser: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    deleteUser match {
      case _: JsSuccess[UserDTO] => Json.toJson(User.deleteUser(deleteUser.get))
      case e : JsError => jsonError(Actions.GET_USER, e)
    }
  }

  private[converter] def getUser(receivedMessage: JsValue): JsValue = {
    val getUser: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    getUser match {
      case _: JsSuccess[UserDTO] => Json.toJson(User.getUser(getUser.get))
      case e : JsError => jsonError(Actions.GET_USER, e)
    }
  }

    private[converter] def updateUser(receivedMessage: JsValue): JsValue = {
    val updateUser: JsResult[UserDTO] = Json.fromJson[UserDTO](receivedMessage)
    updateUser match {
      case _: JsSuccess[UserDTO] => Json.toJson(User.updateUser(updateUser.get))
      case e : JsError => jsonError(Actions.GET_USER, e)
    }
  }
}

