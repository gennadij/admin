package org.genericConfig.admin.client.controllers.listner

import org.genericConfig.admin.client.models.{Register, Start, User}
import org.genericConfig.admin.shared.Actions
import org.scalajs.jquery.JQuery

/**
 * Copyright (C) 2016 Gennadi Heimann genaheimann@gmail.com
 *
 * Created by Gennadi Heimann 09.04.2020
 */
class Mouse {

  def mouseClick(jQueryElem: JQuery, action : String, param : Option[Any] = None): Unit = {
    action match {
        //Actions to Server
      case Actions.GET_USER => jQueryElem.on("click", () => new Start().actionGetUser())
      case Actions.ADD_USER => jQueryElem.on("click", () => new Register().actionAddUser())
      case Actions.GET_CONFIGS => jQueryElem.on("click", () => new User().getConfigs(param))
      case Actions.UPDATE_USER => jQueryElem.on("click", () => new User().updateUsername(param))
        //Actions internal
      case Actions.REGISTER_PAGE => jQueryElem.on("click", () => new Register().register())
      case Actions.START_PAGE => jQueryElem.on("click", () => new Start().start())
      case Actions.USER_PAGE => jQueryElem.on("click", () => new User().showUser(param))
      case Actions.UPDATE_USER_PAGE => jQueryElem.on("click", () => new User().showUpdateUserPage(param))
      case Actions.DELETE_USER_PAGE => jQueryElem.on("click", () => println("DELETE_USER_PAGE"))
      case _ => jQueryElem.on("click", () => println("kein Definition"))
    }
  }
}
